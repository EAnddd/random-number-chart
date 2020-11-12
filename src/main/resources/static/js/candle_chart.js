
var chart;
var dataPoints = [];
var max;

function drawChart() {

    chart = new CanvasJS.Chart("chartContainer", {
        theme: "light1", // "light1", "light2", "dark1", "dark2"
        exportEnabled: true,
        title: {
            text: "Random Sequence"
        },
        subtitles: [{
            text: "Split dataset"
        }],
        axisX: {
            minimum: 0,
            maximum: max
        },
        axisY: {
            prefix: "",
            title: "Values"
        },
        toolTip: {
            // content: "Date: {x}<br /><strong>Value</strong><br />Open: {y[0]}, Close: {y[3]}<br />High: {y[1]}, Low: {y[2]}"
            content: "Block: {x}<br /><strong>Value</strong><br />Average: {y[0]}, PrevAverage: {y[3]}<br />Max: {y[1]}, Min: {y[2]}"
        },
        data: [{
            type: "candlestick",
            yValueFormatString: "##0.00",
            dataPoints: dataPoints
        }]
    });
    var size = $('#part-size').val()
    console.log(size)
    $.ajax({
        method: "GET",
        url: `/candle/sequence_4`,
        data: {size: size},
        success: getDataPointsFromCandleSpring
        })
    // $.get("https://canvasjs.com/data/gallery/javascript/netflix-stock-price.csv", getDataPointsFromCSV);
    // $.get("/candle/sequence_4", {"size": size}, getDataPointsFromCandleSpring)
}

function getDataPointsFromCandleSpring(candleJson) {
    console.log("start ")
    console.log(candleJson.length)
    max = candleJson.length;
    for (var i = 0; i < candleJson.length; i++) {

        dataPoints.push({
            x: [
                i
            ],
            y: [
                parseFloat(candleJson[i]["rightBorder"]),
                parseFloat(candleJson[i]["highLine"]),
                parseFloat(candleJson[i]["lowLine"]),
                parseFloat(candleJson[i]["leftBorder"])
            ]
        })
    }
    console.log("\ntest ")
    console.log(dataPoints)
    chart.render();
    chart.exportChart({format:"jpeg",fileName:"chart"})
}
