/**
 * 
 */
$(document).ready(function () {
	
	$('[data-toggle="tooltip"]').tooltip();

	var dataArray;
	$('#homeTabContent').hide();
	$('#statusTabContent').show();
	$('#guideTabContent').hide();
	$('#homeTab').addClass("active");
	$('#confirmMigration').attr('disabled', 'disabled');

	$('#homeTab').click(function () {
		$('#statusTab').removeClass("active");
		$('#guideTab').removeClass("active");
		tabSwitch('homeTab');
	});
	$('#statusTab').click(function () {
		$('#homeTab').removeClass("active");
		$('#guideTab').removeClass("active");
		tabSwitch('statusTab');
	});
	$('#guideTab').click(function () {
		$('#statusTab').removeClass("active");
		$('#homeTab').removeClass("active");
		tabSwitch('guideTab');
	});
	
	/*
	 * Initiate Migration Process
	 */
	$('#initMigration').click(function(){
		
		//show loading image
		//ajax call
		$.ajax({
			type: "GET",
			url: "/initMigration",
			async: true,
			success: function(data) {
				var rowData = "";
		 		var index;
		 		dataArray = data.dataToBeProcessed;
		 		$('#appPath').html(data.targetApplicationPath);
		 		
		 		for(index = 0; index < data.dataToBeProcessed.length; ++index){
		 			var contentOrPath = data.dataToBeProcessed[index].contentOrPath.replaceAll("<","&lt;");
		 			contentOrPath = contentOrPath.replaceAll(">","&gt;");
		 			var objId = data.dataToBeProcessed[index].dependentOn;
		 			var dependentOn = (data.dataToBeProcessed[index].dependentOn == null || data.dataToBeProcessed[index].dependentOn.trim() == "") 
		 			? "" : objId+"#"+data.dataToBeProcessed[objId-1].action;
		 			
		 			rowData = rowData + "<tr><td>"+data.dataToBeProcessed[index].id+"</td>" +
		 			"<td>"+data.dataToBeProcessed[index].action+"</td>" +
		 			"<td class='dataTD'>"+data.dataToBeProcessed[index].identifier+"</td>" +
		 			"<td>"+data.dataToBeProcessed[index].target+"</td>" +
		 			"<td class='dataTD'>"+contentOrPath+"</td>" +
		 			"<td>"+dependentOn+"</td>" +
		 			"<td id="+data.dataToBeProcessed[index].id+">Not Started</td></tr>";
		 		}
		 		
		 		$('#homeTab').removeClass("active");
				$('#guideTab').removeClass("active");
		 		tabSwitch('statusTab');
		 		$('#configDataTable').html(rowData);
		 		$('#confirmMigration').removeAttr('disabled');
			},
	        error: function (data) {
	        	$('#errorMsgId').html(data.responseJSON.errorMessage);	
	        	$("#myModal").modal('show');
	        }
		});
	});
	
	/*
	 * Starting Migration
	 */
	$('#confirmMigration').click(function(){
		dataArray.sort(function(a, b) { 
			  return parseInt(a.id) - parseInt(b.id);
			});
		
		for(var index = 0; index < dataArray.length; ++index){
			$('#'+dataArray[index].id).css('color', 'yellow');
	 		$('#'+dataArray[index].id).html('In-Progress');
	 		var ajaxBreak = false;
	 		$.ajax({
				type: "POST",
				url: "/processMigration",
				async: false,
				data: {taskId: dataArray[index].id},
				success: function(data) {
					//$('#'+data).css('color', 'green');
					$('#'+data).addClass('successful');
			 		$('#'+data).html('Successful');
					
				},
		        error: function (data) {
		        	if(data.responseJSON.taskId === "0"){
		        		$('#errorMsgId').html(data.responseJSON.errorMessage);	
			        	$("#myModal").modal('show');
		        		ajaxBreak = true;
		        	}
		        	else{
		        		var msg = "";
		        		if(data.responseJSON.info){
		        			$('#'+data.responseJSON.taskId+' a').addClass('warning');
		        			msg = "<h5>Warning</h5>";
		        		}
		        		else{
		        			$('#'+data.responseJSON.taskId+' a').addClass('failure');
		        			msg = "<h5>Failure</h5>";
		        		}
		        		$('#'+data.responseJSON.taskId).html("<a href='#' data-toggle='modal' data-target='#myModal' id='"+data.responseJSON.taskId+"' onClick=populateErrMsgToModal("+data.responseJSON.taskId+") class='errorMessage'> " +
		        				msg + "<input type='hidden' value='"+data.responseJSON.errorMessage+"' id='hiddenEle"+data.responseJSON.taskId+"'></a>");
		        	}
		        }
			});
	 		if(ajaxBreak){
	 			break;
	 		}
		}
		
		$('#confirmMigration').attr('disabled', 'disabled');
	});
});

function populateErrMsgToModal(id){
	$('#errorMsgId').html($('#hiddenEle'+id).val());
}

function tabSwitch(tabName) {
	$('#' + tabName).addClass("active");
	if (tabName == "homeTab") {
		$('#homeTabContent').hide();
		$('#statusTabContent').hide();
		$('#guideTabContent').hide();
	} else if (tabName == "statusTab") {
		$('#homeTabContent').hide();
		$('#statusTabContent').show();
		$('#guideTabContent').hide();
	} else if (tabName == "guideTab") {
		$('#homeTabContent').hide();
		$('#statusTabContent').hide();
		$('#guideTabContent').show();
	}
}