import {validate} from "./validation.js";
import {hideError, renderForm, renderTable, showError} from "./front-view.js";

$("#values-form").submit((e) => {
    e.preventDefault();
    if (!validate()) return;

    $.ajax({
        url: "/",
        method: "GET",
        data: `${$("#values-form").serialize()}&timezone=${new Date().getTimezoneOffset()}`,
        beforeSend: () => {
            setButtonDisabled(true);
        },
        success: (data) => {
            setButtonDisabled(false);
            renderTable(data);

            let graph = $(".area-wrapper");
            let xVal = $("input[name=xVal]").val();
            let yVal = $("input[name=yVal]").val();
            let rVal = $("select[name=rVal]").val();
            let k = graph.width() / 2;

            graph.append(`<div class="dot" style="top: ${k - yVal * k / (1.25 * rVal) - 4}px; left: ${k + xVal * k / (1.25 * rVal) - 4}px" />`);
        }
    });
})

function setValue(value) {
    $("input[name=xVal]").val(value);
}

function setButtonDisabled(isDisabled) {
    $("button[type=submit]").attr("disabled", isDisabled);
}

$(window).on("load", (e) => {
    renderForm();
});

$("#clear_table").on("click", (e) => {
    $.ajax({
        url: "/",
        data: "clear=true",
        method: "GET",
        success: () => { window.location.replace("/"); }
    });
})

$(".value-button").on("click", (e) => {
    e.preventDefault();
    setValue(e.target.value);
})

$("#clear_picture").on("click", (e) => {
    e.preventDefault();
    $.ajax({
        url: "/",
        data: "clear=true",
        method: "GET",
        success: () => {
            window.location.replace("/");
        }
    });
})

$("#go_back").on("click", (e) => {
    e.preventDefault();
    window.location.replace("/");
})

$(".area-wrapper").on("click", (e) => {
    hideError();
    let rInput = $("select[name=rVal]");

    if (!rInput.val()) {
        showError("Параметр R не задан!");
        return;
    }

    let k = $(".area-wrapper").width() / 2;
    let rVal = rInput.val();
    let xVal = rVal * (e.offsetX - k) * 1.25 / k;
    let yVal = rVal * (k - e.offsetY) * 1.25 / k;

    $.ajax({
        url: "/",
        method: "GET",
        data: `xVal=${xVal}&yVal=${yVal}&rVal=${rVal}&timezone=${new Date().getTimezoneOffset()}`,
        beforeSend: () => {
            setButtonDisabled(true);
        },
        success: (data) => {
            setButtonDisabled(false);
            renderTable(data);

            let graph = $(".area-wrapper");

            graph.append(`<div class="dot" style="top: ${e.offsetY - 4}px; left: ${e.offsetX - 4}px;" />`);
        },
    });
})