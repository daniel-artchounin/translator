$(document).ready(function(){
	/* Here, we will make an AJAX GET request to get
	 * some JSON content representing a translation.
	 * Then, we will use it to update our form used
	 * to update a translation.
	 */ 
	$("#languageId").change(function(){
		var $languageId = $(this).val();
		var $contentId = $("#content_id").val();
		$.get('translation_management',{
			action: 'change_translation',
			language_id: $languageId, 
			content_id: $contentId
			},
			function(responseJson) { 
				var $i = 0;
				$.each(responseJson.parts, function(index, deactivatedContentPart){
					$("#activated_language_" + index.toString()).val(deactivatedContentPart.partContent);
				});				
            }
		);
	});
});