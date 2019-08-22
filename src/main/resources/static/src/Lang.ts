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
        $('#dropdown-lang').click( (event: any) => {
            const target: JQuery = $(event.target);
            const lang = target.attr('lang');
            if (!lang) {
                return;
            }

            this.changeLanguage(lang);
        });
    }

    private changeLanguage(lang: string): void {
        $.ajax({
            url: '/lang.son',
            data: {lang},
            success: () => {
                window.location.reload();
            },
        })
    }

    public dropDowns(): void {
        $("#dropdown-lang").toggleClass("show");
    }
}