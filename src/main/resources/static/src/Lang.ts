/**
 * Created by son on 2019-08-22.
 */
import $ from 'jquery';

export class Lang {
    private elNav: JQuery;

    public init(): void {
        this.elNav = $('#navbar');
        this.render();
    }

    private render(): void {
        this.elNav.children('ul').append($.parseHTML($('#langDropdown').text()));
    }

    public dropDowns(): void {
        return;
    }
}