/**
 * Created by son on 2019-03-14.
 */
var theme = {
    regexKeyword: /public|private|if|else|while|for|try|catch|return|new|null|final|break|continue/g,
    regexType: /int|String|List|Map|Set|File|Path/g,
    regexBracket: /\(|\)|\{|\}|\[|\]/g,
    keywordColor: '#93C763',
    typeColor: '#678CB1',
    bracketColor: '#E8E2B7',

    apply : function (text) {
        var result = text;
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
            matchType.forEach(function (item) {
                var _text = result;
                var index = _text.indexOf(item);
                var front = _text.substring(0, index);
                var back = _text.substring(index + item.length, _text.length);

                var font = document.createElement('font');
                font.innerText += item;
                font.color = this.typeColor;

                result = front + font.outerHTML + back;
            }.bind(this));
        }

        // var matchBracket = result.match(this.regexBracket);
        // if (!!matchBracket && matchBracket !== '') {
        //     matchBracket.forEach(function (item) {
        //         var _text = result;
        //         var index = _text.indexOf(item);
        //         var front = _text.substring(0, index);
        //         var back = _text.substring(index + item.length, _text.length);
        //
        //         var span = document.createElement('span');
        //         span.innerText += item;
        //         span.style = this.bracketColor;
        //
        //         result = front + span.outerHTML + back;
        //     }.bind(this));
        // }

        if (result === '') {
            result = text;
        }

        return result;
    }
};