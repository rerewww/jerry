/**
 * Created by son on 2019-03-13.
 */
import $ from 'jquery';

export class clientConfig {
	static removeNodeLimit: number = 200;
	static chartDataLimit: number = 6;
	static readOnlyKeys: string[] = ['serverPort', 'ajpProtocol', 'ajpPort', 'ajpEnabled'];

	static checkedLib(): boolean {
		return $('input[id=libCheck]').is(':checked');
	}

	static checkedLog(): boolean {
		return $('input[id=logCheck]').is(':checked');
	}

	static checkedError(): boolean {
		return $('input[id=errorCheck]').is(':checked');
	}

	static checkedAccess(): boolean {
		return $('input[id=accessCheck]').is(':checked');
	}
}
