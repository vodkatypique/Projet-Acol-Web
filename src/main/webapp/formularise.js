/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function formularise(lien, event, id, action) {
    "use strict";
    /* EmpÃªcher lâ€™ouverture de plusieurs formulaires Ã  la fois :
     * Si un formulaire de modification/suppression est dÃ©jÃ  prÃ©sent, on lâ€™annule */
    var ancien_bouton_annuler = document.getElementById("bouton_annuler");
    if (ancien_bouton_annuler !== null) {
        ancien_bouton_annuler.onclick();
    }

    /* RÃ©cupÃ©ration du contenu actuel */
    var tr = lien.parentElement.parentElement; // ligne du tableau contenant le lien
    var contenu_initial = tr.innerHTML; // contenu Ã  remettre en cas dâ€™annulation
    var cases = tr.cells; // tableau contenant les 4 cases de la ligne du tableau    
    var auteur = cases[0].textContent;
    var titre = cases[1].textContent;
    
    /* Modification de la ligne du tableau */
    if (action === 'modifier') { // les deux premiÃ¨res cases deviennent des champs texte
        // on pourrait crÃ©er ces Ã©lÃ©ments de formulaire Ã  la main mais utiliser innerHTML
        // permet de remplacer tout le contenu de la case dâ€™un coup : câ€™est plus simple
        cases[0].innerHTML = '<input type="text" name="auteur" value="' + auteur + '" />';
        cases[1].innerHTML = '<input type="text" name="titre" value="' + titre + '" />';
    }
    else if (action === 'supprimer') { // on barre le texte pour indiquer quâ€™on va le supprimer
        cases[0].style.textDecoration = "line-through";
        cases[1].style.textDecoration = "line-through";
    }
    else { // action non gÃ©rÃ©e : ne rien faire (ne devrait pas se produire)
        return;
    }
    // dans la case du bouton Confirmer on ajoute aussi les champs cachÃ©s nÃ©cessaires
    cases[2].innerHTML = '<input type="hidden" name="id" value="' + id + '" />' +
                         '<input type="hidden" name="action" value="' + action + '" />' +
                         '<input type="submit" value="Confirmer" />';

    // la derniÃ¨re case doit contenir le bouton Annuler.
    // On crÃ©e ce bouton Ã  la main pour avoir plus de contrÃ´le
    var bouton_annuler = document.createElement("button");
    bouton_annuler.type = "button";
    bouton_annuler.textContent = "Annuler";
    // lâ€™identifiant du bouton permettra de le retrouver pour annuler lâ€™action par script
    // (voir au dÃ©but de cette fonction)
    bouton_annuler.id = "bouton_annuler";
    // annuler a pour effet de remettre la ligne du tableau dans son Ã©tat initial
    bouton_annuler.onclick = function (ev) {
        tr.innerHTML = contenu_initial;
    };
    // la derniÃ¨re case contenait juste un lien, il suffit de remplacer ce lien par notre bouton
    cases[3].replaceChild(bouton_annuler, cases[3].firstElementChild);
    // le script a fonctionnÃ© : ne pas suivre le lien
    event.preventDefault();
}

