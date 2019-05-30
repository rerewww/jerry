/**
 * Created by son on 2019-05-27.
 */
import $ from 'jquery';
import {Server} from "./Server";
import {Setting} from "./Setting";

const obj = {
    server: new Server(),
    setting: new Setting()
};

$( document ).ready( () => {
    $('body').click( (event) => {
        const target = $(event.target);
        const that = target.attr('that');
        const cmd = target.atrr('cmd');
        if (!that || !cmd) {
            return;
        }

        obj[that].cmd.call(obj[that]);
    });
    obj[server].connect();
    // action.getInfos();

    setting.onToggleLogEvent();
    setting.onToggleErrorEvent();
    setting.onToggleAccessEvent();

    // var date = moment().add(0, 'd').format();
    // var chartConfig = {
    //     type: 'line',
    //     data: {
    //         datasets: [{
    //             label: 'success',
    //             backgroundColor: '#4fBB98',
    //             pointBackgroundColor: '#4fBB98',
    //             borderColor: '#4FBB98',
    //             pointBorderColor: '#4FBB98',
    //             data: [{
    //                 x: date,
    //                 y: 0
    //             }],
    //             fill: false
    //         }, {
    //             label: 'fail',
    //             backgroundColor: '#F95F5E',
    //             pointBackgroundColor: '#F95F5E',
    //             borderColor: '#F95F5E',
    //             pointBorderColor: '#F95F5E',
    //             data: [{
    //                 x: date,
    //                 y: 0
    //             }],
    //             fill: false
    //         }]
    //     },
    //     options: {
    //         responsive: false,
    //         maintainAspectRatio: true,
    //         scales: {
    //             xAxes: [{
    //                 type: "time",
    //                 display: true,
    //                 time: {
    //                     unit: 'second',
    //                     displayFormats: {
    //                         quarter: 'HH:mm:ss'
    //                     }
    //                 }
    //             }]
    //         }
    //     }
    // };

    // renderer.drawChart(chartConfig);
});