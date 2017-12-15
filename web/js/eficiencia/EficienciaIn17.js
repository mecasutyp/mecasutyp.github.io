/*
 * @author Daniel Ramìrez Torres
 */
function EficienciaIn17(control){  
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value + "' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;        
    }else{
         control.value=Number(control.value);
        $(control).removeClass("inputerror").addClass("inputok");        
        operacionesEficienciaIn17();
    }
}
function operacionesEficienciaIn17(){
    var valores="";
    var matricula_ini=$("form[name='EficienciaIn17Form'] input[name='matricula_inicial']").val();
    var profe_tiempo=$("form[name='EficienciaIn17Form'] input[name='profe_tiempo']").val();
    if(matricula_ini==0 || profe_tiempo==0 || $("form[name='EficienciaIn17Form'] input").hasClass("inputerror")){
      $("form[name='EficienciaIn17Form'] [name='resultado']").val(0);
    }
    else{
        
    var result=getDecimal(((parseInt(matricula_ini)/parseInt(profe_tiempo))));
    $("form[name='EficienciaIn17Form'] [name='resultado']").val(result);
    }
     valores=valores.concat("-",matricula_ini,",",profe_tiempo,"-") ;   
return valores;

}

function guardarEficienciaIn17(){
    var x="";
    if ($("form[name='EficienciaIn17Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
       $().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
    }else{
        x = operacionesEficienciaIn17();
        $().alerta('verde', 'Datos guardados correctamente.');
    }
    return x;
}