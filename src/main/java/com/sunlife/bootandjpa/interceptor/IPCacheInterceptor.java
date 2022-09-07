package com.sunlife.bootandjpa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunlife.bootandjpa.controller.RegistrationController;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Component
public class IPCacheInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LogManager.getLogger(RegistrationController.class);

	@Autowired
	private CacheManager cacheManager;
	
	public static final String IPADDRESS_LOCKOUTCOUNTER_CACHE_DB = "IPLockoutCounter";
    public static final String IPADDRESS_LOCKOUT_CACHE_DB = "IPLockout";
    public static final String AUDIT_REGIPBLOCK = "REGIPBLOCK";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String ipAddress=request.getRemoteAddr();
		
		Cache ipLockoutCounterCache = cacheManager.getCache(IPADDRESS_LOCKOUTCOUNTER_CACHE_DB);
		Cache ipLockoutCache = cacheManager.getCache(IPADDRESS_LOCKOUT_CACHE_DB);
		logger.info("IP Address-based Cleared from ipLockoutCache :: " + ipLockoutCache.getKeys());
		logger.info("IP Address-based Cleared from ipLockoutCounterCache :: " + ipLockoutCounterCache.getKeys());
		if(checkIPBasedIdentificationLockdown(ipAddress)) {
			request.setAttribute("BOT_DETECTED", "TRUE");
		}
		
		incrementIPBasedIdentificationLockdown(ipAddress);

		if(checkIPBasedIdentificationLockdown(ipAddress)) {
			request.setAttribute("BOT_DETECTED", "TRUE");
		}
		
		return true;
	}
	

	private String getIPAdress(HttpServletRequest request) {
		String ipAddress = request.getHeader("True-Client-IP");
		if(!StringUtils.hasText(ipAddress)) {
			ipAddress = request.getHeader("X-Forwarded-For");
			if(null != ipAddress && ipAddress.indexOf(",") == -1) {
				String[] ipadressArray = ipAddress.split(",");
				if(null != ipadressArray && ipadressArray.length >0) {
					ipAddress = ipadressArray[0];
				}
			}
		}
		
		ipAddress = StringUtils.hasText(ipAddress) ? ipAddress.trim() : ipAddress;
		logger.debug("True-Client-IP ipAddress :: "+request.getHeader("True-Client-IP"));
		logger.debug("X-Forwarded-For ipAddress :: "+request.getHeader("X-Forwarded-For"));
		logger.debug("Final ipAddress :: "+ ipAddress);
		return ipAddress;
	}

	private Boolean checkIPBasedIdentificationLockdown(String ipAddress) {
		// Retrieve the cache database for the server-based identification lockdown.
		Cache ipLockoutCache = cacheManager.getCache(IPADDRESS_LOCKOUT_CACHE_DB);
		Boolean isIdentificationLockdown = false;
		Element cachedValue = null;
		// Check if server-based identification has been locked based on the personal information for the member.
		if (StringUtils.hasText(ipAddress)) {
			cachedValue = ipLockoutCache.get(ipAddress);
			if (cachedValue != null && cachedValue.getValue() != null && (Integer)cachedValue.getValue()==1){
				isIdentificationLockdown = true;
			}
		}
		return isIdentificationLockdown;	

	}


	private void incrementIPBasedIdentificationLockdown(String ipAddress) {

		// Retrieve the cache databases for the IP address attempt counter for Guest Email Validator.
		Cache ipLockoutCounterCache = cacheManager.getCache(IPADDRESS_LOCKOUTCOUNTER_CACHE_DB);
		Cache ipLockoutCache = cacheManager.getCache(IPADDRESS_LOCKOUT_CACHE_DB);

		// Redirect to the CCC error page if the member has exceeded the threshold for identification attempts on the server.
        int identification_failure_threshold = 5;
        logger.debug("identification_failure_threshold = "+identification_failure_threshold);
		Integer newAttemptCount = null;
		Element cachedValue = null;

		// Check if IP address-based identification is to be locked based on the Guest email validator.
		if (ipAddress != null && ipAddress.length() > 0) {
			Integer currentAttemptCount = new Integer(0);
			cachedValue = ipLockoutCounterCache.get(ipAddress);

			// Retrieve the current IP attempt count from the cache database.
			if (cachedValue != null && cachedValue.getValue() != null) {
				currentAttemptCount = (Integer) cachedValue.getValue();

				// Additional logging to inform us if identification failures are common and what kind of failures they are.
				logger.debug("Found cached value: " + cachedValue.getValue() + ", currentAttemptCount = " + currentAttemptCount + ", cache key name = " + ipLockoutCounterCache);
				logger.debug("Did NOT find cached value. Cache key name = " + ipLockoutCounterCache);
			}
			// If the attempt count has exceeded the threshold, initiate identification lockdown.
			newAttemptCount = new Integer(currentAttemptCount + 1);
			if (newAttemptCount < identification_failure_threshold) {

				// Additional logging to inform us if identification failures are common and what kind of failures they are.
				ipLockoutCounterCache.remove(ipAddress);
				ipLockoutCounterCache.put(new Element(ipAddress, newAttemptCount));
			} else {
				//  Additional logging to inform us if identification failures are common and what kind of failures they are.
				ipLockoutCounterCache.remove(ipAddress);
				ipLockoutCache.put(new Element(ipAddress, new Integer(1)));
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

}