/**
 * Created by son on 2019-03-13.
 */
import $ from 'jquery';

export class ClientConfig {
	removeNodeLimit: number = 200;
	chartDataLimit: number = 6;
	readOnlyKeys: string[] = ['serverPort', 'ajpProtocol', 'ajpPort', 'ajpEnabled'];

	public checkedLib(): boolean {
		return $('input[id=libCheck]').is(':checked');
	}

	public checkedLog(): boolean {
		return $('input[id=logCheck]').is(':checked');
	}

	public checkedError(): boolean {
		return $('input[id=errorCheck]').is(':checked');
	}

	public checkedAccess(): boolean {
		return $('input[id=accessCheck]').is(':checked');
	}
}
