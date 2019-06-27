/**
 * Created by son on 2019-05-27.
 */
import $ from 'jquery';
import {Server} from "./Server";
import {Setting} from "./Setting";
import {action} from "./Action"

const obj = {
	server: new Server(),
	setting: new Setting(),
	action: new action()
};

$( document ).ready( () => {
	$('body').click( (event) => {
		const target = $(event.target);
		const that = target.attr('that');
		const cmd = target.attr('cmd');

		obj.setting.clear(target);
		if (!that || !cmd) {
			return;
		}

		obj[that][cmd].call(obj[that], target);
	});
	obj.action.init();
	obj.setting.init();
	obj.server.connect();
});