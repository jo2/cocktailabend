$(function () {
    $('#error').hide();
    $('#diagram').hide();
    $('#mostCalled').hide();
    $.ajax({
        url: "/statistics/data",
        method: "GET",
        success: function (data) {
            $('#diagram').show();
            var ctx = document.getElementById("diagram").getContext('2d');
            var jumbos = [], cocktails = [], options = [];
            for (var i = 0; i < data.length; i++) {
                options.push(data[i].date);
                jumbos.push(data[i].jumboCount);
                cocktails.push(data[i].cocktailCount);
            }

            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: options,
                    datasets: [{
                        label: 'Anzahl Jumbococktails',
                        data: jumbos,
                        backgroundColor: 'lightgreen',
                        borderColor: 'lightgreen',
                        borderWidth: 3,
                        fill: false,
                        lineTension: 0
                    }, {
                        label: 'Anzahl Cocktails',
                        data: cocktails,
                        backgroundColor: 'lightblue',
                        borderColor: 'lightblue',
                        borderWidth: 3,
                        fill: false,
                        lineTension: 0
                    }]
                },
                options: {
                    responsive: false,
                    title: {
                        display: true,
                        text: 'Cocktails'
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false
                    },
                    hover: {
                        mode: 'nearest',
                        intersect: true
                    },
                    scales: {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                defaultFontSize: 14,
                                defaultFontStyle: 'normal',
                                labelString: 'Zeit'
                            },
                            gridLines: {
                                display: false
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                defaultFontSize: 14,
                                defaultFontStyle: 'normal',
                                labelString: 'Anzahl'
                            },
                            ticks: {
                                stepSize: 1
                            }
                        }]
                    }
                }
            });
        },
        error: function (data) {
            $('#error').show();
        }
    });

    $.ajax({
        url: "/statistics/mostCalled",
        method: "GET",
        success: function (data) {
            $('#mostCalled').show();
            var ctx = document.getElementById("mostCalled").getContext('2d');
            var cocktails = [];
            for (var i = 0; i < data.length; i++) {
                cocktails.push({x: (data[i].jumbo ? 'Jumbo ': 'Normal') + data[i].number, y: data[i].called});
            }
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: cocktails,
                options: {
                    responsive: false,
                    title: {
                        display: true,
                        text: 'Meist Ausgerufene'
                    },
                    tooltips: {
                        mode: 'index',
                        intersect: false
                    },
                    hover: {
                        mode: 'nearest',
                        intersect: true
                    },
                    scales: {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                defaultFontSize: 14,
                                defaultFontStyle: 'normal',
                                labelString: 'Cocktail'
                            },
                            gridLines: {
                                display: false
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                defaultFontSize: 14,
                                defaultFontStyle: 'normal',
                                labelString: 'Anzahl'
                            },
                            ticks: {
                                stepSize: 1
                            }
                        }]
                    }
                }
            });
        },
        error: function (data) {
            $('#error').show();
        }
    });
});


