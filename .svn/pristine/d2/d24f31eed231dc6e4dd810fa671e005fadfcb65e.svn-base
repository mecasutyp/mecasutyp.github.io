function cargando(){
    $( "#dialog-cargando" ).dialog({
        height: 150,
        modal: true,
        disabled: true,
        closeText: 'hide',
        show: "fade",
        hide: "fade"
    }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
}
function EnviarPDF(){
    alert("Guarde el PDF para cualquier duda o aclaración.")
    window.open('PDF.msut?ask=Info');
    $('button[name="btnEnviar"]').attr('aria-disabled', false).removeAttr('title');
    $('button[name="btnEnviar"]').attr("disabled", false).removeClass('ui-button-disabled ui-state-disabled').attr();
} 
$(document).ready(function(){
     
    $( "#dialog-confirm" ).dialog({
        autoOpen: false,
        resizable: false,
        height:230,
        width: 470,
        modal: true,
        buttons: {
            "Iniciar Ahora": function() {
                var cancelar = 0;
                $("input[type='checkbox']").each(function(index, domEle){
                    if(!$(domEle).next().hasClass("ui-state-active")){
                        cancelar++;
                        $( "#dialog-confirm" ).dialog( "close" );
                        return false;
                    }
                });
                if(cancelar == 0){
                    window.location.href = "global.msut?ask=validarInicio";
                }else{
                    $().alerta("amarillo","Debes verificar toda la informaci&oacute;n para poder continuar");
                    $().alerta("azul","Para verificar la informaci&oacute;n presione todos los botones \"Verificado\", cada sección se volver&aacute; de color azul cuando est&eacute; verificada.");
                }
            },
            Cancelar: function() {
                $( this ).dialog( "close" );
            }
        }
    });
    
    $("button").button();
    $( "ul li" ).tooltip({
        track: true
    });
    $( "#dialog-cargando" ).dialog("close");
});