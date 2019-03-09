/**
 * Created by son on 2019-03-07.
 */
var setting = {
    onToggleLibEvent: function () {
        var isCheckedPackets = $('input[id=libCheck]').is(':checked');
        if (!isCheckedPackets) {
            $('input[id=libCheck]').trigger('click');
        }
    },
    onToggleParseEvent: function () {
        var isCheckedPackets = $('input[id=ParseCheck]').is(':checked');
        if (!isCheckedPackets) {
            $('input[id=ParseCheck]').trigger('click');
        }
    },
    onToggleErrorEvent: function () {
        var isCheckedPackets = $('input[id=ErrorCheck]').is(':checked');
        if (!isCheckedPackets) {
            $('input[id=ErrorCheck]').trigger('click');
        }
    }
};