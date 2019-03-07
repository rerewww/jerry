/**
 * Created by son on 2019-03-07.
 */
var setting = {
    onToggleLibEvent: function () {
        var isCheckedPackets = $('input[id=libCheck]').is(':checked');
        if (!isCheckedPackets) {
            $('input[id=libCheck]').trigger('click');
        }
    }
};