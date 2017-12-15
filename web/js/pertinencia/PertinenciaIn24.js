//    Document   : Script PertinenciaIn24
//    Author     : Daniel Ramìrez Torres
function PertinenciaIn24(control){
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value + "' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        control.value=Number(control.value);
        $(control).removeClass("inputerror").addClass("inputok");
        operacionesPertinenciaIn24();
        operaciones2PertinenciaIn24();
    }
}
function operacionesPertinenciaIn24(){
    var cadena="-";
    var subtotal1=0;
    var subtotal2=0;
    var valor1=0;
    if ($("form[name='PertinenciaIn24Form'] input").hasClass("inputerror")){
        $("form[name='PertinenciaIn24Form'] input[name='subtotal1']").val(0);
        $("form[name='PertinenciaIn24Form'] input[name='subtotal2']").val(0);
        $("form[name='PertinenciaIn24Form'] input[name='total']").val(0);
    }else{
        for(var j=1;j<13;j++){
            valor1=parseInt($("form[name='PertinenciaIn24Form'] input[name='valor"+j+"']").val());
            cadena=cadena.concat($("form[name='PertinenciaIn24Form'] input[name='valor"+j+"']").val(),",");
            if(j<9){
                subtotal1=parseInt(subtotal1)+parseInt(valor1);
            }
            else{
                subtotal2=parseInt(subtotal2)+parseInt(valor1);
            }
        } 
        $("form[name='PertinenciaIn24Form'] input[name='subtotal1']").val(subtotal1);
        $("form[name='PertinenciaIn24Form'] input[name='subtotal2']").val(subtotal2);
        $("form[name='PertinenciaIn24Form'] input[name='total']").val(subtotal2+subtotal1);
    }
    return(cadena);
}

function operaciones2PertinenciaIn24(){
    var valor=0;
    var profesin=0;
    var  profecon=0;
    var cadena="";
    if ($("form[name='PertinenciaIn24Form'] input").hasClass("inputerror")){
        $("form[name='PertinenciaIn24Form'] input[name='totalprofesores']").val(0);
        $("form[name='PertinenciaIn24Form'] input[name='total_actual']").val(0);
        $("form[name='PertinenciaIn24Form'] input[name='total_ultimos']").val(0);
        $("form[name='PertinenciaIn24Form'] input[name='total_sin']").val(0);
    }else{
        for(var i=1;i<7;i++){
            valor=parseInt($("form[name='PertinenciaIn24Form'] input[name='valor2"+i+"']").val());
            cadena=cadena.concat($("form[name='PertinenciaIn24Form'] input[name='valor2"+i+"']").val(),",");
            if(i<4){
                profecon=parseInt(profecon)+parseInt(valor);
            }
            else{
                profesin=parseInt(profesin)+parseInt(valor);
            }
        }
        $("form[name='PertinenciaIn24Form'] input[name='profesin']").val(profesin);
        $("form[name='PertinenciaIn24Form'] input[name='profecon']").val(profecon);
        $("form[name='PertinenciaIn24Form'] input[name='totalprofesores']").val(profecon+profesin);
        $("form[name='PertinenciaIn24Form'] input[name='total_actual']").val(parseInt($("form[name='PertinenciaIn24Form'] input[name='valor21']").val())+parseInt($("form[name='PertinenciaIn24Form'] input[name='valor24']").val()));
        $("form[name='PertinenciaIn24Form'] input[name='total_ultimos']").val(parseInt($("form[name='PertinenciaIn24Form'] input[name='valor22']").val())+parseInt($("form[name='PertinenciaIn24Form'] input[name='valor25']").val()));
        $("form[name='PertinenciaIn24Form'] input[name='total_sin']").val(parseInt($("form[name='PertinenciaIn24Form'] input[name='valor23']").val())+parseInt($("form[name='PertinenciaIn24Form'] input[name='valor26']").val()));
        
        var ppael=getDecimal(($("form[name='PertinenciaIn24Form'] input[name='profecon']").val() / $("form[name='PertinenciaIn24Form'] input[name='totalprofesores']").val()) * 100);
        if(isNaN(ppael)){
            ppael="0.0 %";
        }    
        
        $("form[name='PertinenciaIn24Form'] input[name='porcentajepael']").val(ppael + " %");
    }
    cadena=cadena.concat("-");
    return cadena;
}

function guardarPertinenciaIn24(){
    var x="";
    var x1="x";
    var cadena="";
    if ($("form[name='PertinenciaIn24Form'] input").hasClass("inputerror")){
        $().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
        cadena="Error";
    }
    else if (parseInt($("form[name='PertinenciaIn24Form'] input[name='total']").val())
        != parseInt($("form[name='PertinenciaIn24Form'] input[name='tota_ptc']").val())){
        $().alerta('amarillo', 'Datos NO guardados. La suma del cuadro 24.1 debe coincidir con el total de profesores de asignatura de la Universidad ("'+$("form[name='PertinenciaIn24Form'] input[name='tota_ptc']").val()+'")');
        cadena="Error"    ;
    }
    else if (parseInt($("form[name='PertinenciaIn24Form'] input[name='totalprofesores']").val())
        != parseInt($("form[name='PertinenciaIn24Form'] input[name='tota_ptc']").val())){
        $().alerta('amarillo', 'Datos NO guardados. La suma total de profesores de asignatura del cuadro 24.2 debe coincidir con el total de Profesores de asignatura de la Universidad ("'+$("form[name='PertinenciaIn24Form'] input[name='tota_ptc']").val()+'")');
        cadena="Error";
    }    
    else{
        x = operacionesPertinenciaIn24();
        x1= operaciones2PertinenciaIn24();
        cadena=x+x1;
           //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='PertinenciaIn24Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "24";
                GuardarComentarios(comentario, indicador);
            }
    // $().alerta('verde', 'Datos guardados correctamente.');
    }
    return cadena;
}
