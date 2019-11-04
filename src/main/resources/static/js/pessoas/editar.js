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

addFileSubmitListeners();