function replaceAtores (html) {
    $('#idAtores').html(html);
    addRemoveAtorListeners();
}

function replacePremiacoes (html) {
    $('#premiacoes').html(html);
    addRemovePremiacaoListeners();
}

function addRemoveAtorListeners() {
    $('button[name="removeAtor"]').click(function (event) {
        event.preventDefault();
        var data = $('form').serialize();
        data += '&removeAtor=' + $(this).val();
        $.post('/admin/filmes/editar', data, replaceAtores);
    });
}

function addRemovePremiacaoListeners() {
    $('button[name="removePremiacao"]').click(function (event) {
        event.preventDefault();
        var data = $('form').serialize();
        data += '&removePremiacao=' + $(this).val();
        $.post('/admin/filmes/editar', data, replacePremiacoes);
    });
}

function addAddAtorListeners() {
    $('button[name="addAtor"]').click(function (event) {
        event.preventDefault();
        var data = $('form').serialize();
        var idNovoAtor = $('#idNovoAtor').val();
        data += '&addAtor=' + idNovoAtor;
        $.post('/admin/filmes/editar', data, replaceAtores);
    });
}

function addAddPremiacaoListeners() {
    $('button[name="addPremiacao"]').click(function (event) {
        event.preventDefault();
        var data = $('form').serialize();
        var novaPremiacao = $('#novaPremiacao').val();
        data += '&addPremiacao=' + novaPremiacao;
        $.post('/admin/filmes/editar', data, replacePremiacoes);
    });
}

addAddAtorListeners();
addAddPremiacaoListeners();
addRemoveAtorListeners();
addRemovePremiacaoListeners();