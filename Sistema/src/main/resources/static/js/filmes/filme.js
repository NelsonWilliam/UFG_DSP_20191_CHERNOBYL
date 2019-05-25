function replaceAvaliacaoFilme (html) {
    $('#avaliacaoFilme').html(html);
    addAvaliarFilmeListeners();
}

function replaceResenhas (html) {
    $('#resenhas').html(html);
    addAvaliarResenhaListeners();
}

function addAvaliarFilmeListeners() {
    $('button[name="avaliarFilme"]').click(function (event) {
        event.preventDefault();
        var data = '&avaliarFilme=' + $(this).val();
        $.get(window.location.pathname, data, replaceAvaliacaoFilme);
    });
}

function avaliarResenha(event, positivo, id) {
    event.preventDefault();
    var data = '&avaliarResenha=' + id;
    data += "&positivo=" + positivo;
    console.log
    $.get(window.location.pathname, data, replaceResenhas);
}

function addAvaliarResenhaListeners() {
    $('button[name="avaliarResenhaPositivo"]').click(function (event) {
        avaliarResenha(event, true, $(this).val());
    });
    $('button[name="avaliarResenhaNegativo"]').click(function (event) {
        avaliarResenha(event, false, $(this).val());
    });
}

addAvaliarFilmeListeners();
addAvaliarResenhaListeners();