function paragraphManager(lien) {
    "use strict";
     var tr = lien.parentElement.parentElement; // ligne du tableau contenant le lien
     var table = tr.parentElement;
     var new_tr = document.createElement("tr");
     var new_th = document.createElement("th");
     var new_th2 = document.createElement("th");
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
     new_th.appendChild(new_choice);
     new_th2.appendChild(button_sup);
     new_tr.appendChild(new_th);
     new_tr.appendChild(new_th2);
     table.insertBefore(new_tr, tr);
  }