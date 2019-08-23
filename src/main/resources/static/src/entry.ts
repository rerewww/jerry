/**
 * Created by son on 2019-05-27.
 */
import $ from 'jquery';
import {Server} from "./Server";
import {Setting} from "./Setting";
import {Action} from "./Action"
import {Lang} from "./Lang";

const obj = {
	server: new Server(),
	setting: new Setting(),
	action: new Action(),
	lang: new Lang()
};

$( document ).ready( () => {
	const body: JQuery = $('body');
	body.attr('class', window['_info'].lang);

	obj.action.getInfos();
	obj.setting.init();
	obj.lang.init();
	obj.server.connect();

	body.click( (event) => {
		const target = $(event.target);
		const that = target.attr('that');
		const cmd = target.attr('cmd');

		obj.setting.clear(target);
		if (!that || !cmd) {
			return;
		}

		obj[that][cmd].call(obj[that], target);
	});
});