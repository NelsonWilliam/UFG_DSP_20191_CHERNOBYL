function replaceAvaliacaoFilme (html) {
    $('#avaliacaoFilme').html(html);
    addAvaliarFilmeListeners();
}

function replaceResenhas (html) {
    $('#resenhas').html(html);
    addAvaliarResenhaListeners();
}

function replaceTopicos (html) {
    $('#topicos').html(html);
    addAvaliarTopicoListeners();
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

function avaliarTopico(event, positivo, id) {
    event.preventDefault();
    var data = '&avaliarTopico=' + id;
    data += "&positivo=" + positivo;
    console.log
    $.get(window.location.pathname, data, replaceTopicos);
}

function addAvaliarResenhaListeners() {
    $('button[name="avaliarResenhaPositivo"]').click(function (event) {
        avaliarResenha(event, true, $(this).val());
    });
    $('button[name="avaliarResenhaNegativo"]').click(function (event) {
        avaliarResenha(event, false, $(this).val());
    });
}

function addAvaliarTopicoListeners() {
    $('button[name="avaliarTopicoPositivo"]').click(function (event) {
        avaliarTopico(event, true, $(this).val());
    });
    $('button[name="avaliarTopicoNegativo"]').click(function (event) {
        avaliarTopico(event, false, $(this).val());
    });
}

addAvaliarFilmeListeners();
addAvaliarResenhaListeners();
addAvaliarTopicoListeners();