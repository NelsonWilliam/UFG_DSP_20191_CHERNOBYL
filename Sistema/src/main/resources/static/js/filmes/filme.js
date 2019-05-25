function replaceAvaliacao (html) {
    $('#avaliacao').html(html);
    addAvaliacaoVotoListeners();
}

function addAvaliacaoVotoListeners() {
    $('button[name="avaliacaoVoto"]').click(function (event) {
        event.preventDefault();
        var data = '&votar=' + $(this).val();
        $.get(window.location, data, replaceAvaliacao);
    });
}

addAvaliacaoVotoListeners();