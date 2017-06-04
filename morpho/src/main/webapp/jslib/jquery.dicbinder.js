/**
 * Data Dictionary Binder
 * 
 * 
 * 
 */
(function($) {
	$.extend({
		dicbinder : function(dictionary, code, queryParams) {
			var dic = $(document).data(dictionary);
			if (dic) {
				return dic[code];
			}
			$.loaddictionary(dictionary, queryParams);
			dic = $(document).data(dictionary);
			if (dic) {
				return dic[code];
			}
			return code;
		}
	});
	$.loaddictionary = function(dictionary, queryParams) {
		var dic = $(document).data(dictionary);
		if (!dic) {
			var param = $.extend({}, queryParams || {});
			$.ajax({
				type : 'get',
				url : 'backstage/code/' + dictionary,
				data : param,
				dataType : 'json',
				success : function(data) {
					dic = {};
					for (var i = 0; i < data.length; i++) {
						var v = data[i]['Value'] + '';
						var s = data[i]['Text'];
						dic[v] = s;
					}
					$(document).data(dictionary, dic);
				},
				error : function() {
				}
			});
		}
	};
})(jQuery);
