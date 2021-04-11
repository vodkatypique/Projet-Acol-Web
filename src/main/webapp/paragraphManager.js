function addChoice(lien) {
    "use strict";
     var tr = lien.parentElement.parentElement; // ligne du tableau contenant le lien
     var table = tr.parentElement;
     var new_tr = document.createElement("tr");
     var new_th = document.createElement("th");
     var new_th2 = document.createElement("th");
     var new_th3 = document.createElement("th");
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
     isConditionnal.value = "check";
     isConditionnal.onclick = addSelector(this);
     var LabelConditionnal = document.createElement("label");
     LabelConditionnal.innerHTML = "choix conditionnel";
     LabelConditionnal.appendChild(isConditionnal);
     new_th.appendChild(new_choice);
     new_th2.appendChild(LabelConditionnal);
     new_th3.appendChild(button_sup);
     new_tr.appendChild(new_th);
     new_tr.appendChild(new_th2);
     new_tr.appendChild(new_th3);
     table.insertBefore(new_tr, tr);
     
  }
  

function blockChoice(checkbox){
        "use strict";    
            var table = document.getElementsByTagName("table");
            var listInput = table[0].getElementsByTagName("input");
            var listButton = table[0].getElementsByTagName("button");
            for(i=0; i < listInput.length; i++) {
                listInput[i].disabled = checkbox.checked;
            }
            for(i=0; i < listButton.length; i++){
                listButton[i].disabled = checkbox.checked;
            }
}

function addSelector(checkbox){
     "use strict";
     var th = checkbox.parentElement.parentElement;
     var th2 = document.createElement("th");
     var selector = document.getElementById("selector");
     var selectorConditionnal = selector.cloneNode(true);
     selectorConditionnal.id = "selectorCond";
     selectorConditionnal.style.visibility="visible";
     selectorConditionnal.disabled = false;
     
     var th2.appendChild(selectorConditionnal);
     
}