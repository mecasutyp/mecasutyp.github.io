//APROVECHAMIENTO ACADÉMICO DEL NIVEL 5B VERSIÓN 2004 SEGÚN CARRERA
//author Daniel Ramìrez Torres
//Actualización 2016: Salvador Zamora Arias

function indicador2(control,nuMC,version){
    if (/^([0-9]{1,2}(\.[0-9]{0,1}))*$/.test(control.value)|| /^([0-9])*$/.test(control.value)){
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);
        MayoresEficaciaIn2(version,control);
        if(version==2009){
            operaciones2(nuMC);
        }
        if(version==2004){
            operaciones2v2004(nuMC);    
        }else{
            operaciones2(nuMC);
        }
    }else{
        $().alerta("amarillo", "El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
    }

}

function MayoresEficaciaIn2(version,control) {
    //    if (version>2004){
    if(control.value>0){
        if(control.value >10){
            $(control).globo("enable").globo("update", "<center>Calificaci&oacute;n Inv&aacute;lida<br/></center>").removeClass("inputok").addClass("inputerror");
        }
        else{
            $(control).globo("disable").removeClass("inputerror").addClass("inputok");
        }
    }
    else{
        $(control).globo("disable");
    }
}


function operaciones2(nC){
    var nP=$("form[name='EficaciaIn2Form'] input[name='numPr"+nC+"']").val(),contSep=0,contEne=0,contMay=0,sumaSep=0,sumaEne=0,sumaMay=0,contTo=0,sumaTot=0;
    if (!$("form[name='EficaciaIn2Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        for(var i=1;i<=nP;i++){
            var cont0=0,suma=0;
            if(parseFloat($("form[name='EficaciaIn2Form'] input[name='sep-dic_"+nC+"_"+i+"']").val())!=0){
                cont0++;
                suma+=parseFloat($("form[name='EficaciaIn2Form'] input[name='sep-dic_"+nC+"_"+i+"']").val());
                sumaSep+=parseFloat($("form[name='EficaciaIn2Form'] input[name='sep-dic_"+nC+"_"+i+"']").val());
                contSep++;
            }
            if(parseFloat($("form[name='EficaciaIn2Form'] input[name='ene-abril_"+nC+"_"+i+"']").val())!=0){
                cont0++;
                suma+=parseFloat($("form[name='EficaciaIn2Form'] input[name='ene-abril_"+nC+"_"+i+"']").val());
                sumaEne+=parseFloat($("form[name='EficaciaIn2Form'] input[name='ene-abril_"+nC+"_"+i+"']").val());
                contEne++;
            }        
            if(parseInt($("form[name='EficaciaIn2Form'] input[name='may-ago_"+nC+"_"+i+"']").val())!=0){
                suma+=parseFloat($("form[name='EficaciaIn2Form'] input[name='may-ago_"+nC+"_"+i+"']").val());
                sumaMay+=parseFloat($("form[name='EficaciaIn2Form'] input[name='may-ago_"+nC+"_"+i+"']").val());
                cont0++;
                contMay++;
            }
            if(getDecimal((suma/cont0))>0){
                contTo++;
                sumaTot+=getDecimal((suma/cont0));
            }
            if(cont0!=0){
                $("form[name='EficaciaIn2Form'] input[name='promCal"+nC+"_"+i+"']").val(getDecimal((suma/cont0)));
            }else{
                $("form[name='EficaciaIn2Form'] input[name='promCal"+nC+"_"+i+"']").val(0);
                
            }
        }
        if(contSep!=0){
            $("form[name='EficaciaIn2Form'] input[name='tot_sep-dic_"+nC+"']").val(getDecimal((sumaSep/contSep)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='tot_sep-dic_"+nC+"']").val(0);
        }
        if(contEne!=0){
            $("form[name='EficaciaIn2Form'] input[name='tot_ene-abril_"+nC+"']").val(getDecimal((sumaEne/contEne)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='tot_ene-abril_"+nC+"']").val(0);
        }
        if(contMay!=0){
            $("form[name='EficaciaIn2Form'] input[name='tot_may-ago_"+nC+"']").val(getDecimal((sumaMay/contMay)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='tot_may-ago_"+nC+"']").val(0);
        }
        if(contTo!=0){
            $("form[name='EficaciaIn2Form'] input[name='total"+nC+"']").val(getDecimal((sumaTot/contTo)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='total"+nC+"']").val(0);
        }
    }else{
        $().alerta("amarillo","La informaci&oacute;n capturada contiene errores.");
    }
}

function operacionesEficaciaIn2(nC){
    if (!$("form[name='EficaciaIn2Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        operaciones2(nC);
    }  
}

function totalAprovechamiento(){
    if (!$("form[name='EficaciaIn2Form'] input").hasClass("inputerror")){
        var numC=$("form[name='EficaciaIn2Form'] input[name='numC']").val(),tot_sep=0,c_tot_sep=0,tot_ene=0,ctot_ene=0,tot_may=0,ctot_may=0,tot_p=0,ctot_p=0;
        for(var i=1;i<numC;i++){
            operaciones2(i);
            if(parseFloat($("form[name='EficaciaIn2Form'] input[name='tot_sep-dic_"+i+"']").val())>0){
                tot_sep+=parseFloat($("form[name='EficaciaIn2Form'] input[name='tot_sep-dic_"+i+"']").val());
                c_tot_sep++;
            }
            if(parseFloat($("form[name='EficaciaIn2Form'] input[name='tot_ene-abril_"+i+"']").val())>0){
                tot_ene+=parseFloat($("form[name='EficaciaIn2Form'] input[name='tot_ene-abril_"+i+"']").val());
                ctot_ene++;
            }
            if(parseFloat( $("form[name='EficaciaIn2Form'] input[name='tot_may-ago_"+i+"']").val())>0){
                tot_may+= parseFloat($("form[name='EficaciaIn2Form'] input[name='tot_may-ago_"+i+"']").val());
                ctot_may++;
            }
            if(parseFloat($("form[name='EficaciaIn2Form'] input[name='total"+i+"']").val())>0){
                tot_p+=parseFloat($("form[name='EficaciaIn2Form'] input[name='total"+i+"']").val());
                ctot_p++;
            }
         
        }
        if(c_tot_sep!=0){
            $("form[name='EficaciaIn2Form'] input[name='promsep']").val(getDecimal((tot_sep/c_tot_sep)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='promsep']").val(0);
        }
        if(tot_ene!=0){
            $("form[name='EficaciaIn2Form'] input[name='promene']").val(getDecimal((tot_ene/ctot_ene)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='promene']").val(0);
        }
        if(tot_may!=0){
            $("form[name='EficaciaIn2Form'] input[name='prommay']").val(getDecimal((tot_may/ctot_may)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='prommay']").val(0);
        }
        if(ctot_p!=0){
            $("form[name='EficaciaIn2Form'] input[name='prompro']").val(getDecimal((tot_p/ctot_p)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='prompro']").val(0);    
        }
    }
}

function valoresEficaciaIn2(){
    var numC=$("form[name='EficaciaIn2Form'] input[name='numC']").val(),cdn="-";
        if ($("form[name='EficaciaIn2Form'] input").hasClass("inputerror")){
            cdn="Error";
        }    
        else{
    for(var j=1;j<numC;j++){
        for(var i=1;i<=$("form[name='EficaciaIn2Form'] input[name='numPr"+j+"']").val();i++){
            cdn+=$("form[name='EficaciaIn2Form'] input[name='sep-dic_"+j+"_"+i+"']").attr('id')+",";
            cdn+=$("form[name='EficaciaIn2Form'] input[name='sep-dic_"+j+"_"+i+"']").val()+",";            
            cdn+=$("form[name='EficaciaIn2Form'] input[name='ene-abril_"+j+"_"+i+"']").val()+",";
            cdn+=$("form[name='EficaciaIn2Form'] input[name='may-ago_"+j+"_"+i+"']").val()+"-";
        }  
    }
    //$().alerta('verde', 'Datos guardados correctamente.');
        }
        var comentario = $("form[name='EficaciaIn2Form'] input[name='comentario2']").val();
        if(comentario != "Sin comentarios"){
            var indicador = "2";
            GuardarComentarios(comentario, indicador);
        }
    return cdn;
}

function operaciones2v2004(nC){
    var nP=$("form[name='EficaciaIn2Form'] input[name='numPr"+nC+"']").val(),contSep=0,contEne=0,contMay=0,sumaSep=0,sumaEne=0,sumaMay=0,contTo=0,sumaTot=0;
    if (!$("form[name='EficaciaIn2Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        for(var i=1;i<=nP;i++){
            var prom=0,cont0=0,suma=0;
            if(parseInt($("form[name='EficaciaIn2Form'] input[name='sep-dic_"+nC+"_"+i+"']").val())!=0){
                cont0++;
                suma+=parseFloat($("form[name='EficaciaIn2Form'] input[name='sep-dic_"+nC+"_"+i+"']").val());
                sumaSep+=parseFloat($("form[name='EficaciaIn2Form'] input[name='sep-dic_"+nC+"_"+i+"']").val());
                contSep++;
            }
            if(parseFloat($("form[name='EficaciaIn2Form'] input[name='ene-abril_"+nC+"_"+i+"']").val())!=0){
                cont0++;
                suma+=parseFloat($("form[name='EficaciaIn2Form'] input[name='ene-abril_"+nC+"_"+i+"']").val());
                sumaEne+=parseFloat($("form[name='EficaciaIn2Form'] input[name='ene-abril_"+nC+"_"+i+"']").val());
                contEne++;
            }        
            if(parseFloat($("form[name='EficaciaIn2Form'] input[name='may-ago_"+nC+"_"+i+"']").val())!=0){
                suma+=parseFloat($("form[name='EficaciaIn2Form'] input[name='may-ago_"+nC+"_"+i+"']").val());
                sumaMay+=parseFloat($("form[name='EficaciaIn2Form'] input[name='may-ago_"+nC+"_"+i+"']").val());
                cont0++;
                contMay++;
            }
            if(getDecimal((suma/cont0))>0){
                contTo++;
                sumaTot+=getDecimal((suma/cont0));
            }
            if(cont0!=0){
                $("form[name='EficaciaIn2Form'] input[name='promCal"+nC+"_"+i+"']").val(getDecimal((suma/cont0)));
            }else{
                $("form[name='EficaciaIn2Form'] input[name='promCal"+nC+"_"+i+"']").val(0);
                
            }
        }
        if(contSep!=0){
            $("form[name='EficaciaIn2Form'] input[name='2004tot_sep-dic_"+nC+"']").val(getDecimal((sumaSep/contSep)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='2004tot_sep-dic_"+nC+"']").val(0);
        }
        if(contEne!=0){
            $("form[name='EficaciaIn2Form'] input[name='2004tot_ene-abril_"+nC+"']").val(getDecimal((sumaEne/contEne)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='2004tot_ene-abril_"+nC+"']").val(0);
        }
        if(contMay!=0){
            $("form[name='EficaciaIn2Form'] input[name='2004tot_may-ago_"+nC+"']").val(getDecimal((sumaMay/contMay)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='2004tot_may-ago_"+nC+"']").val(0);
        }
        if(contTo!=0){
            $("form[name='EficaciaIn2Form'] input[name='2004total"+nC+"']").val(getDecimal((sumaTot/contTo)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='2004total"+nC+"']").val(0);
        }
    }
}

function totalAprovechamiento2004(){
    if (!$("form[name='EficaciaIn2Form'] input").hasClass("inputerror")){
        totalAprovechamiento();
        var c=$("form[name='EficaciaIn2Form'] input[name='numC']").val(),contSep=0,sumSep=0,contEne=0,sumEne=0,contMay=0,sumMay=0,contPro=0,sumPro=0;
        operaciones2v2004(c-1);
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='2004tot_sep-dic_"+(c-1)+"']").val())>0) {
            contSep++;
            sumSep+=parseFloat($("form[name='EficaciaIn2Form'] input[name='2004tot_sep-dic_"+(c-1)+"']").val());
        }
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='promsep']").val())>0){
            contSep++;
            sumSep+=parseFloat($("form[name='EficaciaIn2Form'] input[name='promsep']").val());
        }
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='2004tot_ene-abril_"+(c-1)+"']").val())>0) {
            contEne++;
            sumEne+=parseFloat($("form[name='EficaciaIn2Form'] input[name='2004tot_ene-abril_"+(c-1)+"']").val());
        }
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='promene']").val())>0) {
            contEne++;
            sumEne+=parseFloat($("form[name='EficaciaIn2Form'] input[name='promene']").val());
        }
    
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='2004tot_may-ago_"+(c-1)+"']").val())>0) {
            contMay++;
            sumMay+=parseFloat($("form[name='EficaciaIn2Form'] input[name='2004tot_may-ago_"+(c-1)+"']").val());
        }
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='prommay']").val())>0) {
            contMay++;
            sumMay+=parseFloat($("form[name='EficaciaIn2Form'] input[name='prommay']").val());
        }
    
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='2004total"+(c-1)+"']").val())>0) {
            contPro++;
            sumPro+=parseFloat($("form[name='EficaciaIn2Form'] input[name='2004total"+(c-1)+"']").val());
        }
        if(parseFloat($("form[name='EficaciaIn2Form'] input[name='prompro']").val())>0) {
            contPro++;
            sumPro+=parseFloat($("form[name='EficaciaIn2Form'] input[name='prompro']").val());
        }
        if(contSep!=0){
            $("form[name='EficaciaIn2Form'] input[name='final_sep']").val(getDecimal((sumSep/contSep)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='final_sep']").val(0);
        }
        if(contEne!=0){
            $("form[name='EficaciaIn2Form'] input[name='final_ene']").val(getDecimal((sumEne/contEne)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='final_ene']").val(0);
        }
        if(contMay!=0){
            $("form[name='EficaciaIn2Form'] input[name='final_may']").val(getDecimal((sumMay/contMay)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='final_may']").val(0);
        }
        if(contPro!=0){
            $("form[name='EficaciaIn2Form'] input[name='final_pro']").val(getDecimal((sumPro/contPro)));
        }else{
            $("form[name='EficaciaIn2Form'] input[name='final_pro']").val(0);
        }
    }
}