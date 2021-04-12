function addChoice(lien) {
    "use strict";
     var tr = lien.parentElement.parentElement; // ligne du tableau contenant le lien
     var table = tr.parentElement;
     var new_tr = document.createElement("tr");
     var new_th0 = document.createElement("th");
     var new_th1 = document.createElement("th");
     var new_th2 = document.createElement("th");
     var new_th3 = document.createElement("th");
     var isAlreadyExist = document.createElement("input");
     isAlreadyExist.type ="checkbox";
     isAlreadyExist.name = "isAlreadyExist";
     isAlreadyExist.value="true";
     isAlreadyExist.onclick = function() {
         changeInputChoice(this);
     };
     var labelAlreadyExist = document.createElement("label");
     labelAlreadyExist.innerHTML = "Paragraphe  déjà existant";
     labelAlreadyExist.appendChild(isAlreadyExist);
     var defaultExist = document.createElement("input");
     defaultExist.type = "hidden";
     defaultExist.name = "isAlreadyExist";
     defaultExist.value = "false";
     var new_choice = document.createElement("input");
     new_choice.type="text";
     new_choice.name="choice";
     new_choice.required="required";
     var button_sup = document.createElement("button");
     button_sup.type = "button";
     button_sup.textContent = "Supprimer";
     button_sup.onclick = function() {
         table.removeChild(new_tr);
     }; 
     var isConditionnal = document.createElement("input");
     isConditionnal.type = "checkbox";
     isConditionnal.name = "isConditionnal";
     isConditionnal.value = "true";
     isConditionnal.onclick = function() {
                    addSelectorConditonnal(this);
     };
     var LabelConditionnal = document.createElement("label");
     LabelConditionnal.innerHTML = "choix conditionnel";
     LabelConditionnal.appendChild(isConditionnal);
     var defaultCondtionnal = document.createElement("input");
     defaultCondtionnal.type = "hidden";
     defaultCondtionnal.name = "condition";
     defaultCondtionnal.value = "-1";
     new_th0.appendChild(labelAlreadyExist);
     new_th0.appendChild(defaultCondtionnal);
     new_th1.appendChild(new_choice);
     new_th2.appendChild(LabelConditionnal);
     new_th2.appendChild(defaultCondtionnal);
     new_th3.appendChild(button_sup);
     new_tr.appendChild(new_th0);
     new_tr.appendChild(new_th1);
     new_tr.appendChild(new_th2);
     new_tr.appendChild(new_th3);
     table.insertBefore(new_tr, tr);
     
  }
  

function blockChoice(checkbox){
        "use strict";    
            var table = document.getElementsByTagName("table");
            var listInput = table[0].getElementsByTagName("input");
            var listButton = table[0].getElementsByTagName("button");
            for(var i=0; i < listInput.length; i++) {
                listInput[i].disabled = checkbox.checked;
            }
            for(var i=0; i < listButton.length; i++){
                listButton[i].disabled = checkbox.checked;
            }
}

function addSelectorConditonnal(checkbox){
     "use strict";
    var th = checkbox.parentElement.parentElement;
    if(checkbox.checked) {
            var th2 = document.createElement("th");
            var selector = document.getElementById("selector");
            var selectorConditionnal = selector.cloneNode(true);
            selectorConditionnal.id = "selectorCond";
            selectorConditionnal.style.visibility="visible";
            selectorConditionnal.disabled = false;
            selectorConditionnal.name="condition";
            th2.appendChild(selectorConditionnal);
            th.parentElement.insertBefore(th2, th.nextElementSibling);
            th.lastElementChild.disabled = true;
     } else {
            th.parentElement.removeChild(th.nextElementSibling);
            th.lastElementChild.disabled = false;

     }
}

function  changeInputChoice(checkbox){
    "use strict";
    var th = checkbox.parentElement.parentElement;
    var th2 = document.createElement("th");
    if(checkbox.checked) {
            var selector = document.getElementById("selector");
            var selectorInput = selector.cloneNode(true);
            selectorInput.id = "selectorChoice";
            selectorInput.style.visibility="visible";
            selectorInput.disabled = false;
            selectorInput.name="choice";
            th2.appendChild(selectorInput);
     } else {
            var text = document.createElement("input");
            text.type="text";
            text.name="choice";
            text.required="required";
            th2.appendChild(text);
    }
    th.parentElement.replaceChild(th2, th.nextElementSibling);
    th.lastElementChild.disabled = checkbox.checked;

}