/**
 * Created by son on 2019-03-14.
 */
var theme = {
	regexKeyword: /public|private|if|else|while|for|try|catch|return|new|null|final|break|continue|true|false/g,
	regexType: /int|File|Path|MultipartFile|Iterator|boolean|char|@ResponseBody|@RequestMapping|@RequestParam|HttpServletRequest|HttpServletResponse/g,
	regexAnnotation: /@ResponseBody|@RequestMapping|@RequestParam|@Autowired/g,
	keywordColor: '#CC7832',
	typeColor: '#BBB529',
	annotationColor: '#BBB529',
	numberColor: '#606366',

	apply : function (text) {
		var result = text;

		if (text.indexOf(':') > 0) {
			var number = result.substring(0, text.indexOf(':') + 1);
			var back = result.substring(text.indexOf(':') + 1, text.length);

			var font = document.createElement('font');
			font.innerText += number;
			font.color = this.numberColor;

			result = font.outerHTML + back;
		}

		var matchAnnotation = text.match(this.regexAnnotation);
		if (!!matchAnnotation && matchAnnotation !== '') {
			matchAnnotation.forEach(function (item) {
				var _text = result;
				var index = _text.indexOf(item);
				var front = _text.substring(0, index);
				var back = _text.substring(index + item.length, _text.length);

				var font = document.createElement('font');
				font.innerText += item;
				font.color = this.annotationColor;

				result = front + font.outerHTML + back;
			}.bind(this));
		}

		var matchKeyword = text.match(this.regexKeyword);
		if (!!matchKeyword && matchKeyword !== '') {
			matchKeyword.forEach(function (item) {
				var _text = result;
				var index = _text.indexOf(item);
				var front = _text.substring(0, index);
				var back = _text.substring(index + item.length, _text.length);

				var font = document.createElement('font');
				font.innerText += item;
				font.color = this.keywordColor;

				result = front + font.outerHTML + back;
			}.bind(this));
		}

		var matchType = result.match(this.regexType);
		if (!!matchType && matchType !== '') {
			var _text = result;
			var temp = '';
			var back = '';
			matchType.forEach(function (item) {
				var index = _text.indexOf(item);

				var front = _text.substring(0, index);
				back = _text.substring(index + item.length, _text.length);
				if ((_text.charAt(index - 1) === ";" && _text.charAt(index + item.length) === ' ')
					|| _text.charAt(index - 1) === ' ') {
					var font = document.createElement('font');
					font.innerText += item;
					font.color = this.typeColor;

					temp += front + font.outerHTML;
				} else {
					temp += front + item;
				}
				_text = back;
			}.bind(this));
			result = temp + back;
		}

		if (result === '') {
			result = text;
		}

		return result;
	}
};