function replaceAtores (html) {
    $('#atores').html(html);
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

function addFileSubmitListeners() {
    $('#img-form').submit(function(){
        var isOk = true;
        $('input[type=file][data-max-size]').each(function(){
            if(typeof this.files[0] !== 'undefined'){
                var maxSize = parseInt($(this).attr('data-max-size'),10),
                size = this.files[0].size;
                isOk = maxSize > size;
                if (!isOk)
                    $("#img-form-err").text("O tamanho do arquivo não deve exceder " + maxSize + " byte(s).")
                return isOk;
            }
        });
        return isOk;
    });
}

addAddAtorListeners();
addAddPremiacaoListeners();
addRemoveAtorListeners();
addRemovePremiacaoListeners();
addFileSubmitListeners();