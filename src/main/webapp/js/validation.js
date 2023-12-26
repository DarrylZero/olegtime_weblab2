import { hideError, showError } from "./front-view.js";

export function validate() {
    hideError();
    return validateX() & validateY() & validateR();
}

function validateX() {
    if ($("input[name=xVal]").val().length) {
        return true;
    }
    showError("Ошибка валидации X!");
    return false;
}

function validateY() {
    const MAX_Y = 3;
    const MIN_Y = -3;
    let valY = $("input[name=yVal]").val().replace(",", ".");

    if (!(/^(0$|-?\d*(\.\d*$)?|-?0\.\d*)$/.test(valY))) {
        showError("Y должен задаваться числом!");
        return false;
    }
    if (!(parseInt(valY) >= MIN_Y && parseInt(valY) <= MAX_Y && (!/^-?5[.]0+[1-9]+0*/.test(valY)))) {
        showError("Y не входит в требуемый диапазон!");
        return false;
    }
    return true;
}

function validateR() {
    if ($("select[name=rVal]").length) {
        return true;
    }
    showError("Ошибка валидации R!");
    return false;
}
