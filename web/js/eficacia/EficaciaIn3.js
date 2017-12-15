//* @author Daniel Ramírez Torres
//PORCENTAJE PROMEDIO CUATRIMESTRAL DE REPROBACIÓN

function indicador3(control){
    if (control.value == "")
        control.value = 0;
    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("amarillo", "El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    } else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);
        obtenervalores3();
    }
}
function guardarEficaciaIn3(){
    var x="";
        var existearchivo=verificararchivo("EficaciaIn3Form");
    if ($("form[name='EficaciaIn3Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
       // $().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
        x="Error";
    }else{
        var tamanomaximo=2097152;
        var seenvia=true;
        if(existearchivo){
            if($("form[name='EficaciaIn3Form'] input[name='archivo']")[0].files[0].size > tamanomaximo){
                $().alerta('rojo', 'Error al guardar. El tama&ntilde;o del archivo es mayor al permitido.');
                  $("form[name='EficaciaIn3Form'] input[name='archivo']").removeClass("inputok").addClass("inputerror");
                  seenvia=false;
            }else{   
                var filexext=$("form[name='EficaciaIn3Form'] input[name='archivo']")[0].value;
                var extension = (filexext.substring(filexext.lastIndexOf("."))).toLowerCase(); 
                 var base64txt="";
                base64( $("form[name='EficaciaIn3Form'] input[name='archivo']")[0], function(data){
                    base64txt=data.base64;
                    var universidad=$("form[name='EficaciaIn3Form'] input[name='nomuni']").val();  
                    var IdUni=$("form[name='EficaciaIn3Form'] input[name='IdUni']").val();  
                    var IdPer=$("form[name='EficaciaIn3Form'] input[name='IdPer']").val();  
                    if(parseInt(data.size) < tamanomaximo){//SI ES MENOR A 2 MB (2097152 BYTES) SE ENVIARA
                         enviarcorreo(base64txt, universidad, "3", extension, IdUni, IdPer);
                    }
                }) 
            }   
        }else{
            $().alerta('rojo', 'Error al guardar. Es necesario enviar un archivo.');
            seenvia=false;
        }
        
        if(seenvia){
            var comentario = $("form[name='EficaciaIn3Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "3";
                GuardarComentarios(comentario, indicador);
            }
            x = obtenervalores3();
        }else{
            x="Error";
        }
        
        //$().alerta('verde', 'Datos guardados correctamente.');
    }
    return x;
}

function calcularporcentajes(){
    var numC=parseInt($("form[name='EficaciaIn3Form'] input[name='numC']").val());
    var tot_t=0;
    for(var z=1;z<=(numC-1);z++){
        var arrcolumna = [];
        var sum1=0;
        var numProg=parseInt($("form[name='EficaciaIn3Form'] input[name='numProg-"+z+"']").val());
        for(var m=0;m<6;m++){
            arrcolumna[m]=0;
        }
        for(var a=1;a<=numProg;a++){
            arrcolumna[0]+=parseInt($("form[name='EficaciaIn3Form'] input[name='mat_sep,"+z+"-"+a+"']").val());
            arrcolumna[1]+=parseInt($("form[name='EficaciaIn3Form'] input[name='rep_sep,"+z+"-"+a+"']").val());
            arrcolumna[2]+=parseInt($("form[name='EficaciaIn3Form'] input[name='mat_ene,"+z+"-"+a+"']").val());
            arrcolumna[3]+=parseInt($("form[name='EficaciaIn3Form'] input[name='rep_ene,"+z+"-"+a+"']").val());
            arrcolumna[4]+=parseInt($("form[name='EficaciaIn3Form'] input[name='mat_may,"+z+"-"+a+"']").val());
            arrcolumna[5]+=parseInt($("form[name='EficaciaIn3Form'] input[name='rep_may,"+z+"-"+a+"']").val());
        }
        for(var m=0;m<6;m++){
            $("form[name='EficaciaIn3Form'] input[name='totalcol,"+z+"-"+m+"']").val(arrcolumna[m]);    
        }
        
        var re1= 0;
        var re2= 0;
        var re3= 0;
        if(arrcolumna[1] > 0){
            re1= getDecimal(arrcolumna[1]/arrcolumna[0]*100);
        }
        if(arrcolumna[3] > 0){
            re2= getDecimal(arrcolumna[3]/arrcolumna[2]*100);
        }
        if(arrcolumna[5] > 0){
            re3= getDecimal(arrcolumna[5]/arrcolumna[4]*100);
        }
        var ret= getDecimal((re1+re2+re3)/3);

        $("form[name='EficaciaIn3Form'] input[name='tot_1,"+z+"']").val(re1 + " %");
        $("form[name='EficaciaIn3Form'] input[name='tot_2,"+z+"']").val(re2 + " %");
        $("form[name='EficaciaIn3Form'] input[name='tot_3,"+z+"']").val(re3 + " %");
        $("form[name='EficaciaIn3Form'] input[name='tot_4,"+z+"']").val(ret + " %");
        tot_t += ret;
    }
        $("form[name='EficaciaIn3Form'] input[name='tot_t']").val( getDecimal( tot_t / (numC-1))+ " %");
    
}

function obtenervalores3(){
    var numC=parseInt($("form[name='EficaciaIn3Form'] input[name='numC']").val());
    var cadena="-",total_t=0,num=0;
    
for(var z=1;z<=(numC-1);z++){
    var numProg=parseInt($("form[name='EficaciaIn3Form'] input[name='numProg-"+z+"']").val());
    for(var a=1;a<=numProg;a++){
        cadena+=$("form[name='EficaciaIn3Form'] input[name='niv,"+z+"']").val()+",";
        cadena+=$("form[name='EficaciaIn3Form'] input[name='id_prog,"+z+"-"+a+"']").val()+",";
        cadena+=$("form[name='EficaciaIn3Form'] input[name='mat_sep,"+z+"-"+a+"']").val()+",";
        cadena+=$("form[name='EficaciaIn3Form'] input[name='rep_sep,"+z+"-"+a+"']").val()+",";
        cadena+=$("form[name='EficaciaIn3Form'] input[name='mat_ene,"+z+"-"+a+"']").val()+",";
        cadena+=$("form[name='EficaciaIn3Form'] input[name='rep_ene,"+z+"-"+a+"']").val()+",";
        cadena+=$("form[name='EficaciaIn3Form'] input[name='mat_may,"+z+"-"+a+"']").val()+",";
        cadena+=$("form[name='EficaciaIn3Form'] input[name='rep_may,"+z+"-"+a+"']").val();
        cadena+="-";
        if(parseInt($("form[name='EficaciaIn3Form'] input[name='rep_sep,"+z+"-"+a+"']").val())> parseInt($("form[name='EficaciaIn3Form'] input[name='mat_sep,"+z+"-"+a+"']").val())){
            $("form[name='EficaciaIn3Form'] input[name='rep_sep,"+z+"-"+a+"']").globo("enable").globo("update",  "<center>No puede ser mayor el <br/>n&uacute;mero de Alumnos Reprobados, al de la<br/>Matric&uacute;la inicial atendida<br/>cuatrimestre Septiembre-Diciembre</center>").removeClass("inputok").addClass("inputerror");
        }else{
            $("form[name='EficaciaIn3Form'] input[name='rep_sep,"+z+"-"+a+"']").globo("disable").removeClass("inputerror").addClass("inputok")   
        }

        if(parseInt($("form[name='EficaciaIn3Form'] input[name='rep_ene,"+z+"-"+a+"']").val())> parseInt($("form[name='EficaciaIn3Form'] input[name='mat_ene,"+z+"-"+a+"']").val())){
            $("form[name='EficaciaIn3Form'] input[name='rep_ene,"+z+"-"+a+"']").globo("enable").globo("update",  "<center>No puede ser mayor el <br/>n&uacute;mero de Alumnos Reprobados, al de la<br/>Matric&uacute;la inicial atendida<br/>cuatrimestre Enero-Abril</center>").removeClass("inputok").addClass("inputerror");
        }else{
            $("form[name='EficaciaIn3Form'] input[name='rep_ene,"+z+"-"+a+"']").globo("disable").removeClass("inputerror").addClass("inputok")   
        }
        
         if(parseInt($("form[name='EficaciaIn3Form'] input[name='rep_may,"+z+"-"+a+"']").val())> parseInt($("form[name='EficaciaIn3Form'] input[name='mat_may,"+z+"-"+a+"']").val())){
            $("form[name='EficaciaIn3Form'] input[name='rep_may,"+z+"-"+a+"']").globo("enable").globo("update",  "<center>No puede ser mayor el <br/>n&uacute;mero de Alumnos Reprobados, al de la<br/>Matric&uacute;la inicial atendida<br/>cuatrimestre Mayo-Agosto</center>").removeClass("inputok").addClass("inputerror");
        }else{
            $("form[name='EficaciaIn3Form'] input[name='rep_may,"+z+"-"+a+"']").globo("disable").removeClass("inputerror").addClass("inputok")   
        }
    }
}
       calcularporcentajes();
       console.log(cadena);
    return cadena;
}
