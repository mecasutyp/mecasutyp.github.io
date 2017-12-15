/* 
 * @author Daniel Ramìrez Torres
 * Capacidad Instalada de la Universidad
 * Actualización 2016: Salvador Zamora Arias
 */
function noControles13(){
    var i = 0;
    while(document.forms["EficienciaIn13Form"][i].name != "fin"){
        i++;
    }
    return i;
}
function operacionesEficienciaIn13(){
    var cadena = "-";
    var sumaunidades=0;
    var sumaesp=0;
    var sumacap=0;
    for(i = 0; i < noControles13("EficienciaIn13Form"); i++){
        if (document.forms["EficienciaIn13Form"][i].name.indexOf("val_") != -1){
            var observaciones = replaceAll(document.forms["EficienciaIn13Form"][i+4].value,",","");
            var x = document.forms["EficienciaIn13Form"][i].name.substring(7);
            x = x.concat(",", document.forms["EficienciaIn13Form"][i+1].value,",", 
                document.forms["EficienciaIn13Form"][i+3].value,",","'", 
                observaciones,"'");
            if(document.forms["EficienciaIn13Form"][i+1].value!="" || document.forms["EficienciaIn13Form"][i+1].value!=0){
                document.forms["EficienciaIn13Form"][i+2].value= parseInt(document.forms["EficienciaIn13Form"][i].value) * parseInt(document.forms["EficienciaIn13Form"][i+1].value);
            }else{              
                document.forms["EficienciaIn13Form"][i+2].value=0; 
            }            
            cadena = cadena.concat(x,"-");
            if(document.forms["EficienciaIn13Form"][i+1].value!=0 ||document.forms["EficienciaIn13Form"][i+1].value!=""){
                sumaunidades=parseInt(sumaunidades)+parseInt(document.forms["EficienciaIn13Form"][i+1].value);
            }else{  
                document.forms["EficienciaIn13Form"][i+1].value=0;
            }            
            if(document.forms["EficienciaIn13Form"][i+2].value!=0 || document.forms["EficienciaIn13Form"][i+2].value!=""){
                sumaesp=parseInt(sumaesp)+parseInt(document.forms["EficienciaIn13Form"][i+2].value);
            }else{          
                document.forms["EficienciaIn13Form"][i+2].value=0;
            }
            if(document.forms["EficienciaIn13Form"][i+3].value==0 || document.forms["EficienciaIn13Form"][i+3].value!=""){
                sumacap=parseInt(sumacap)+parseInt(document.forms["EficienciaIn13Form"][i+3].value);
            }else{   
                document.forms["EficienciaIn13Form"][i+3].value=0;            
            }
            i+=4;
        }
    }
    turnoEficienciaIn13();
    if(noControles13("EficienciaIn13Form")>6){
        $("form[name='EficienciaIn13Form'] [name='tot_uni']").val(sumaunidades);
        $("form[name='EficienciaIn13Form'] [name='tot_esp']").val(sumaesp);
        $("form[name='EficienciaIn13Form'] [name='tot_cap']").val(sumacap);
        var turno_vespertino=$("form[name='EficienciaIn13Form'] [name='resultado_vespertino']").val();
        var total=$("form[name='EficienciaIn13Form'] [name='total']").val();
        var multiplicacion=parseFloat(sumaesp)*parseFloat(turno_vespertino);
        $("form[name='EficienciaIn13Form'] [name='res_mat/ves']").val(getDecimal(multiplicacion/100));
        var res_final=$("form[name='EficienciaIn13Form'] [name='res_mat/ves']").val();
        $("form[name='EficienciaIn13Form'] [name='res_final']").val(parseInt(sumaesp)+parseFloat(res_final));    
   
        if(total!=0 && $("form[name='EficienciaIn13Form'] [name='res_final']").val()!=0){
            $("form[name='EficienciaIn13Form'] [name='tuem']").val(Math.round(total/$("form[name='EficienciaIn13Form'] [name='res_final']").val()*100)*1/1); 
        }else{
            $("form[name='EficienciaIn13Form'] [name='tuem']").val(0);
        }
    }
        //CALCULA TUE
        var mat_total=parseFloat($("form[name='EficienciaIn13Form'] [name='mat_total']").val());
        var tot_esp=parseFloat($("form[name='EficienciaIn13Form'] [name='tot_esp']").val());
        if(mat_total==0 || tot_esp==0){
            $("form[name='EficienciaIn13Form'] [name='tue']").val(0 + " %"); 
        }else{
            $("form[name='EficienciaIn13Form'] [name='tue_1']").val(getDecimal(mat_total/tot_esp*100) + " %"); 
        }  
     return cadena;
}
function EficienciaIn13(control){
    var controles = new Array();
    var j = 0;
    while(document.forms["EficienciaIn13Form"][j].name != "fin"){
        controles.push(document.forms["EficienciaIn13Form"][j].name);
        j++;
    }
    for(i = 0; i < controles.length; i++){
        if (document.forms["EficienciaIn13Form"].elements[controles[i]].value == "")
            document.forms["EficienciaIn13Form"].elements[controles[i]].value = 0;
    }
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("amarillo", "El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        valoresEficienciaIn13();
        return;
    }else{
        operacionesEficienciaIn13();
        valoresEficienciaIn13();     
        control.value=Number(control.value);
        $(control).removeClass("inputerror").addClass("inputok");
    }
}

function guardarEficienciaIn13(){
    var x="";
    var x1="";
    var cadena="";
    if (parseInt($("form[name='EficienciaIn13Form'] input[name='total_alumnos']").val()) ==parseInt($("form[name='EficienciaIn13Form'] input[name='turno_matutino']").val())+parseInt($("form[name='EficienciaIn13Form'] input[name='turno_vespertino']").val()) ){       
        $("form[name='EficienciaIn13Form'] input[name='total']").removeClass("inputerror").addClass("inputok");          
    }    
    if ($("form[name='EficienciaIn13Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        //$().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
        cadena="Error";
    }else{       
         //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='EficienciaIn13Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "13";
                GuardarComentarios(comentario, indicador);
            }
        x = operacionesEficienciaIn13();        
        x1=turnoEficienciaIn13();
        cadena=x+x1;
    //$().alerta('verde', 'Datos guardados correctamente.');
    }
    return cadena;
}

function turnoEficienciaIn13(){
    var turno_matutino=$("form[name='EficienciaIn13Form'] [name='turno_matutino']").val();
    var turno_vespertino=$("form[name='EficienciaIn13Form'] [name='turno_vespertino']").val();
    var tot_esp=$("form[name='EficienciaIn13Form'] [name='tot_esp']").val();
    if(turno_matutino==0 && turno_vespertino==0 ){
        $("form[name='EficienciaIn13Form'] [name='resultado_matutino']").val(0);
        $("form[name='EficienciaIn13Form'] [name='resultado_vespertino']").val(0); 
        $("form[name='EficienciaIn13Form'] [name='total']").val(0);
        $("form[name='EficienciaIn13Form'] [name='resultado_total']").val(0);
    }else{
        $("form[name='EficienciaIn13Form'] [name='total']").val(parseInt(turno_matutino)+parseInt(turno_vespertino));
        var total=$("form[name='EficienciaIn13Form'] [name='total']").val();
        var resultado_matutino=getDecimal(parseFloat((turno_matutino)/(parseFloat(total))*100));
        var resultado_vespertino=getDecimal(parseFloat((turno_vespertino)/(parseFloat(total))*100));
        $("form[name='EficienciaIn13Form'] [name='resultado_matutino']").val(resultado_matutino);
        $("form[name='EficienciaIn13Form'] [name='resultado_vespertino']").val(resultado_vespertino);
        $("form[name='EficienciaIn13Form'] [name='resultado_total']").val(resultado_vespertino+resultado_matutino);
        if(tot_esp==0){
            $("form[name='EficienciaIn13Form'] [name='tue']").val(0); 
        }else{
            $("form[name='EficienciaIn13Form'] [name='tue']").val(Math.round((total/tot_esp*100)*1)/1); 
        }             
    }
    var x="_";
    x = x.concat(turno_matutino,",",turno_vespertino,"_")
    return x;
}

function valoresEficienciaIn13(){
    if ($("form[name='EficienciaIn13Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        for(var i = 0; i < noControles13("EficienciaIn13Form"); i++){
            document.forms["EficienciaIn13Form"][i+2].value=0;            
            i+=4;
        }
        if(noControles13("EficienciaIn13Form")>6 ){
            $("form[name='EficienciaIn13Form'] [name='tot_uni']").val(0);
            $("form[name='EficienciaIn13Form'] [name='tot_esp']").val(0);
            $("form[name='EficienciaIn13Form'] [name='tot_cap']").val(0);
            $("form[name='EficienciaIn13Form'] [name='res_final']").val(0);    
        }
        $("form[name='EficienciaIn13Form'] [name='resultado_matutino']").val(0);
        $("form[name='EficienciaIn13Form'] [name='resultado_vespertino']").val(0);
        $("form[name='EficienciaIn13Form'] [name='resultado_total']").val(0);
        $("form[name='EficienciaIn13Form'] [name='total']").val(0);
        $("form[name='EficienciaIn13Form'] [name='res_mat/ves']").val(0);
        $("form[name='EficienciaIn13Form'] [name='tue']").val(0); 
        $("form[name='EficienciaIn13Form'] [name='tuem']").val(0); 
        
    }    
}

