let error = $(".error-text");
let table = $(".table");

export function showError(errorText) {
    error.text(errorText);
    error.css({
        display: "block"
    });
}

export function hideError() {
    error.css({
        display: "none"
    });
}

export function renderTable(data) {
    $(".table-block").css("display", "block");
    $(".buttons-block").css("display", "block");
    $(".values-block").css("display", "none");
    table.empty();
    table.append(data);
}

export function renderForm() {
    $(".table-block").css("display", "none");
    $(".buttons-block").css("display", "none");
    $(".values-block").css("display", "block");
}