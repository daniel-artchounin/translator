$(document).ready(function(){
	$("#languageId").change(function(){
		var $languageId = $(this).val();
		var $contentId = $("#content_id").val();
		// alert($languageId.toString() + "_" + $contentId.toString()); // Test
		$.get('translation_management',{
			action: 'change_translation',
			language_id: $languageId, 
			content_id: $contentId
			},
			function(responseJson) { 
				var $i = 0;
				// alert($languageId.toString() + "_" + $contentId.toString()); // Test
				$.each(responseJson.parts, function(index, deactivatedContentPart){
					// alert(deactivatedContentPart.partContent); // Test
					// alert("#activatedlanguage_" + index.toString()); // Test
					$("#activated_language_" + index.toString()).val(deactivatedContentPart.partContent);
				});				
				// alert(responseJson); // Test
            }
		);
	});
});