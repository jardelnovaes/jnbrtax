/**
 * App TaxRules functions
 */

$( document ).ready(function() {
	$('#transactionType_id').change(function() {
		selectId = "#operation_id";
		$.ajax({
	        type:"GET",
	        url : operationsPath +"/getByTransType",
	        data : { transTypeId: this.value},
	        success : function(data) {        	
	        	//alert($("#operation_id option:first-child").val());
	        	firstOpt = $(selectId +" option:first-child"); //
	        	$(selectId).empty(); //remove all child nodes
	        	$(selectId).append(firstOpt);
	        	for(var i = 0; i < data.length; i++){
	                var newOption = $('<option value="' + data[i].id +'">'+data[i].name+'</option>');
	                $(selectId).append(newOption);
	            }
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            alert("Error: \nCode: " + xhr.status +"\nInformation: "+ thrownError);
	            //alert(xhr.responseText);
	        }	        
	    });
	});

	$(".tabField").keydown(function(e) {
		var code = e.keyCode || e.which;
		if (code == 9) {
	        e.preventDefault();
	        var tabId = $(this).attr("data-tabId");	        
	        $(tabId).trigger('click');
	    }
	});
	
});
