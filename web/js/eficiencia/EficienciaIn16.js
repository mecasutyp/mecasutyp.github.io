/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function EficienciaIn16(control){
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '" +control.value+ "' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        $("form[name='EficienciaIn16Form'] input[name='detla1']").val(0);
        $("form[name='EficienciaIn16Form'] input[name='detla2']").val(0);
        return;
    }else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);
        validarMatricula16();
        var libros=parseInt($("form[name='EficienciaIn16Form'] input[name='libros']").val());
        var titulos=parseInt($("form[name='EficienciaIn16Form'] input[name='titulos']").val());
        var matricula=parseInt($("form[name='EficienciaIn16Form'] input[name='matricula']").val());

        if (/^([0-9])*$/.test($("form[name='EficienciaIn16Form'] input[name='libros']").val())
            && /^([0-9])*$/.test($("form[name='EficienciaIn16Form'] input[name='titulos']").val())){
            if (titulos>libros){
                $("form[name='EficienciaIn16Form'] input[name='titulos']").globo("enable")
                .globo("update", "<center>No pueden existir<br/>m&aacute;s libros que t&iacute;tulos</center>")
                .removeClass("inputok").addClass("inputerror").globo("show");
            }else{
                $("form[name='EficienciaIn16Form'] input[name='titulos']")
                .removeClass("inputerror").addClass("inputok").globo("disable");
            }
        }
        if (!$("form[name='EficienciaIn16Form'] input").hasClass("inputerror")){
            //operaciones
            if (matricula!=0){
                $("form[name='EficienciaIn16Form'] input[name='detla1']").val(getDecimal(libros/matricula));
                $("form[name='EficienciaIn16Form'] input[name='detla2']").val(getDecimal(titulos/matricula));
            }else{
                $("form[name='EficienciaIn16Form'] input[name='detla1']").val(0);
                $("form[name='EficienciaIn16Form'] input[name='detla2']").val(0);
            }
        }else{
            $("form[name='EficienciaIn16Form'] input[name='detla1']").val(0);
            $("form[name='EficienciaIn16Form'] input[name='detla2']").val(0);
        }
    }
    if ($('form[name="EficienciaIn16Form"] input').hasClass("inputerror")){
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("update", "<center>Indicador con <br/> errores, favor <br/> de revisar.</center>").globo("show");
    }else{
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("hide");
    }
}

function validarMatricula16(){
    if ($("form[name='EficienciaIn16Form'] input[name='matricula']").val()==0){        
        $("form[name='EficienciaIn16Form'] input[name='matricula']").globo("enable")
        .globo("update", "<center>Por favor, realice <br/> el llenado del <br/> indicador 12 <br/> de Eficacia</center>")
        .removeClass("inputok").addClass("inputerror").globo("show");
    }else{
        $("form[name='EficienciaIn16Form'] input[name='matricula']").globo("disable")
        .removeClass("inputerror").addClass("inputok");
    }
}

function eficienciaIn16Operaciones(){
    var libros=parseInt($("form[name='EficienciaIn16Form'] input[name='libros']").val());
    var titulos=parseInt($("form[name='EficienciaIn16Form'] input[name='titulos']").val());
    var matricula=parseInt($("form[name='EficienciaIn16Form'] input[name='matricula']").val());
    $("form[name='EficienciaIn16Form'] input[name='detla1']").val(getDecimal(libros/matricula));
    $("form[name='EficienciaIn16Form'] input[name='detla2']").val(getDecimal(titulos/matricula));
}

function guardarEficIn16(){
    var cadena="";
    if (!$("form[name='EficienciaIn16Form'] input").hasClass("inputerror")){
        var libros=parseInt($("form[name='EficienciaIn16Form'] input[name='libros']").val());
        var titulos=parseInt($("form[name='EficienciaIn16Form'] input[name='titulos']").val());
        var revistas=parseInt($("form[name='EficienciaIn16Form'] input[name='revistas']").val());
        var bibliotecas=parseInt($("form[name='EficienciaIn16Form'] input[name='bibliotecas']").val());
        cadena=cadena.concat(libros, ",", titulos, ",", revistas, ",", bibliotecas);
        //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='EficienciaIn16Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "16";
                GuardarComentarios(comentario, indicador);
            }
       // $().alerta('verde', 'Datos guardados correctamente.');
    }else{
        //$().alerta('rojo', 'Datos NO guardados. Verifiquelos Por favor');
        cadena="Error";
    }
       return cadena;
}