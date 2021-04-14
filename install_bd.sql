--------------------------------------------------------------------------------
-- Création de la base de donneés
--------------------------------------------------------------------------------

DROP TABLE Choice;
DROP TABLE UserBookHistory;
DROP TABLE UserEditingParagraph;
DROP TABLE UserAccess;
DROP TABLE Paragraph;
DROP TABLE UserTable;
DROP TABLE Book;
DROP SEQUENCE SeqBook;
DROP SEQUENCE SeqUser;

CREATE TABLE Book(
    idBook INT NOT NULL,
    titleBook VARCHAR(50),
    isPublished INT, --boolean
    isOpen INT, --boolean
    superAuthor VARCHAR(50),
    CONSTRAINT pk_id_book PRIMARY KEY (idBook)
);


CREATE TABLE Paragraph(
    idBook INT NOT NULL,
    numParagraph INT NOT NULL,
    paragraphTitle VARCHAR(200) NOT NULL,
    text VARCHAR(2000) NOT NULL,
    author VARCHAR(50) NOT NULL,
    isEnd INT, --boolean
    isValidate INT, --boolean
    isAccessible INT, --boolean
    CONSTRAINT fk_paragraph_idBook FOREIGN KEY (idBook) REFERENCES Book(idBook) ON DELETE CASCADE,
    CONSTRAINT pk_paragraph PRIMARY KEY (idBook, numParagraph)
);


CREATE TABLE UserTable(
    idUser INT NOT NULL UNIQUE,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(64) NOT NULL,
    CONSTRAINT pk_userTable PRIMARY KEY (login)
);


CREATE TABLE UserAccess(
    idBook INT NOT NULL,
    idUser INT NOT NULL,
    CONSTRAINT fk_userAccess_idBook FOREIGN KEY (idBook) REFERENCES Book(idBook) ON DELETE CASCADE,
    CONSTRAINT fk_userAccess_idUser FOREIGN KEY (idUser) REFERENCES UserTable(idUser) ON DELETE CASCADE,
    CONSTRAINT pk_userAccess PRIMARY KEY (idBook, idUser)
);



CREATE TABLE UserBookHistory(
    idBook INT NOT NULL,
    idUser INT NOT NULL,
    history VARCHAR(200),
    CONSTRAINT fk_userBookHistory_idBook FOREIGN KEY (idBook) REFERENCES Book(idBook),
    CONSTRAINT fk_userBookHistory_idUser FOREIGN KEY (idUser) REFERENCES UserTable(idUser),
    CONSTRAINT pk_userBookHistory PRIMARY KEY (idBook, idUser)
);


CREATE TABLE Choice(
    idBook INT NOT NULL,
    numParagraphCurrent INT NOT NULL,
    numParagraphNext INT NOT NULL,
    numParagraphConditional INT,
    CONSTRAINT fk_Choice_idBook FOREIGN KEY (idBook) REFERENCES Book(idBook) ON DELETE CASCADE,
    CONSTRAINT pk_Choice PRIMARY KEY (idBook, numParagraphCurrent, numParagraphNext)
);

CREATE TABLE UserEditingParagraph(
    idUser INT NOT NULL,
    idBook INT NOT NULL,
    numParagraph INT NOT NULL,
    CONSTRAINT fk_Editing_Book FOREIGN KEY (idBook) REFERENCES Book(idBook) ON DELETE CASCADE,
    CONSTRAINT fk_Editing_UserTable FOREIGN KEY (idUser) REFERENCES UserTable(idUser) ON DELETE CASCADE,
    CONSTRAINT pk_Editing_UserEditParagraph PRIMARY KEY (idUser, idBook, numParagraph)
);


CREATE SEQUENCE SeqBook;
CREATE SEQUENCE SeqUser;

--------------------------------------------------------------------------------
-- Remplissage de la base de donneés
--------------------------------------------------------------------------------

INSERT INTO UserTable(idUser, login, password)
VALUES (SeqUser.NEXTVAL, 'Thibault', '2ec75386bb0d5b1fb510b1a60c1b5ad7e0599250000e03c9ae4ac44e6c57e485'); -- zjemgkjmihg
INSERT INTO UserTable(idUser, login, password)
VALUES (SeqUser.NEXTVAL, 'Nicolas', '1d6442ddcfd9db1ff81df77cbefcd5afcc8c7ca952ab3101ede17a84b866d3f3'); -- 1234
INSERT INTO UserTable(idUser, login, password)
VALUES (SeqUser.NEXTVAL, 'Clement', '4f7e447fc5bb395dd8293654a1c7d7f8a1ba152eb8925021191e9dab23140c53'); -- ir0nmaN
INSERT INTO UserTable(idUser, login, password)
VALUES (SeqUser.NEXTVAL, 'Chloe', 'e8065cbeb0b5c542d763a808c1a5c4e09945512a336fc0c3674b7e6df73aa3e4'); -- JaimelACOL



INSERT INTO Book(idBook, titleBook, isPublished, isOpen, superAuthor)
VALUES (SeqBook.NEXTVAL, 'Les aventures de Shrek !', 1, 1, 'Thibault');

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 1, 'Il était une fois, dans un marais,',  
'un joli ogre tout vert y vivait paisiblement dans un tronc d''arbre.',
 'Thibault', 0, 1, 1);
INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 2, 'Vous êtes un chevalier en mission pour attaquer un dragon.',
 'Cependant, en vous aventurant dans ce même marais pour chercher le dragon, 
vous vous retrouvez né à né avec Shrek.
 A première vue, il vous effraye, mais il n''a pas l''air méchant, 
peut être qu''il vous aidera dans votre mission si vous demandez gentillement. Que faites vous ?',
 'Thibault', 0, 1, 1);
INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 3, 'Vous décidez d''attaquer l''ogre.', 
'L''ogre esquive votre épée et vous vous enfuyez lâchement. 
Vous avez découvert ce qu''était un ogre énervé...
mais vous ne pouvez pas continuer votre mission sans votre épée. Vous avez perdu !',
 'Thibault', 1, 1, 1);
INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 4, 'Vous décidez de demander de l''aide à l''ogre pour terasser le dragon.',
 'L''ogre semble comprendre vos mots mais ne semble pas adhérer à votre cause. 
Il vous hurle dessus et vous demande de partir de son marais. 
Au moins, vous ne vous êtes pas fait manger. Vous avez gagné !',
 'Nicolas', 1, 1, 1);

INSERT INTO Book(idBook, titleBook, isPublished, isOpen, superAuthor)
VALUES (SeqBook.NEXTVAL, 'Les aventures de Shrek 2 !', 0, 1, 'Thibault');

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 1, 'Il était une fois...',  
'un ogre, un âne et un petit biscuit.',
 'Thibault', 0, 1, 1);

INSERT INTO Book(idBook, titleBook, isPublished, isOpen, superAuthor)
VALUES (SeqBook.NEXTVAL, 'La forêt maudite', 1, 0, 'Chloe');

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 1, 'Il était une fois, dans une contrée fort fort lointaine,',  
'une forêt maudite. La légende racontait qu''un grand trésor s''y cachait. Poussé par vos amis qui vous attendent à la sortie, vous avez décidé de vous aventurer à l''intérieur, en quête de ce trésor. Un peu après y être entré, une intersection se dégage. ',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 2, 'Vous revenez sur vos pas.',  
'Vous retrouvez l''entree de la forêt. Vos amis se fichent de vous car vous n''avez pas trouvé le trésor. PERDU !',
 'Chloe', 1, 1, 1);


INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 3, 'Vous allez à gauche.',  
'Vous marchez quelques kilomètres et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 4, 'Vous allez à droite.',  
'Vous marchez quelques kilomètres et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);


INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 5, 'Vous allez à gauche.',  
'Vous marchez quelques kilomètres et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 6, 'Vous allez à droite.',  
'Vous marchez quelques kilomètres et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 7, 'Vous allez à gauche.',  
'Vous marchez quelques kilomètres et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 8, 'Vous allez à droite.',  
'Vous marchez quelques kilomètres. Soudainement, vous voyez une série de 12 chiffres gravés dans un arbre. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 18, 'Vous vous approchez et mémorisez les numéros.',  
'Après avoir mémorisé les numéros, vous continuez votre chemin et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 19, 'Vous ignorez les signes et poursuivez votre route.',  
'Vous continuez votre chemin et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 9, 'Vous allez à gauche.',  
'Vous continuez votre périple lorsque soudainement, un hurlement vous transperce les tympans. Pris de panique...',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 10, 'Vous allez à droite.',  
'Vous êtes toujours en train de marcher lorsque soudainement, vous apercevez une racine d''arbre juste devant vous...',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 11, 'Vous vous mettez à courir.',  
'Vous courez sans vraiment savoir où vous allez. Au bout d''un moment, vous reprenez votre calme, mais vous êtes complètement perdu. Les jours passent et vous ne retrouvez jamais votre chemin. Vous finissez par mourir de soif. PERDU !',
 'Chloe', 1, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 12, 'Vous faites le mort.',  
'Vous vous allongez sur le sol et priez pour que rien ne vous arrive. Mais peu de temps après, une créature terrifiante vient à votre rencontre. C''est un ours. Malheureusement pour vous, il a compris votre subterfuge et vous mange tout cru. PERDU !',
 'Chloe', 1, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 13, 'Vous trébuchez.',  
'Trébuchant sur la racine, vous vous étalez sur le sol. Vous avez très mal mais prenez soudainement conscience de quelque chose : un coffre est enterré juste sous votre nez !',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 14, 'Vous évitez la racine.',  
'Vous marchez quelques kilomètres et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 15, 'Vous vous relevez, ignorez le coffre et poursuivez votre chemin.',  
'Vous marchez quelques kilomètres et arrivez de nouveau devant une intersection. Que faites-vous ?',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 16, 'Vous ouvrez le coffre.',  
'Mais oui, bien-sûr, il s''agissait du trésor !!! Quelle perspicacité ! Mais le coffre est trop lourd pour être soulevé. Le seul moyen de l''ouvrir est de trouver la bonne combinaison de 12 chiffres qui forme son code.',
 'Chloe', 0, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 17, 'Vous utilisez la série de chiffres que vous aviez mémorisée.',  
'Mais c''est bien-sûr ! Le coffre s''ouvre. Tout heureux, vous récupérez le trésor. Nul ne sait si vous arriverez à sortir de cette forêt, mais au moins, vous avez atteint votre objectif ! GAGNé !',
 'Chloe', 1, 1, 1);

INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (SeqBook.CURRVAL, 20, 'Vous tentez l''ensemble des combinaisons possibles',  
'Hélas, vous mourrez d''épuisement avant d''avoir pu trouver ce maudit code... PERDU !',
 'Chloe', 1, 1, 1);


INSERT INTO UserAccess(idBook, idUser)
VALUES (1, 1);
INSERT INTO UserAccess(idBook, idUser)
VALUES (1, 2);
INSERT INTO UserAccess(idBook, idUser)
VALUES (1, 3);
INSERT INTO UserAccess(idBook, idUser)
VALUES (1, 4);
INSERT INTO UserAccess(idBook, idUser)
VALUES (2, 1);
INSERT INTO UserAccess(idBook, idUser)
VALUES (2, 2);
INSERT INTO UserAccess(idBook, idUser)
VALUES (2, 3);
INSERT INTO UserAccess(idBook, idUser)
VALUES (2, 4);
INSERT INTO UserAccess(idBook, idUser)
VALUES (3, 4);
INSERT INTO UserAccess(idBook, idUser)
VALUES (3, 2);


-- l'historique se vérifiera par la pratique

-- Shrek
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (1, 1, 2, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (1, 2, 3, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (1, 2, 4, -1);

INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 1, 2, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 1, 3, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 1, 4, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 3, 5, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 3, 6, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 4, 5, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 4, 8, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 5, 7, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 5, 10, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 6, 5, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 6, 8, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 7, 9, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 7, 6, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 8, 18, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 8, 19, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 9, 11, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 9, 12, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 10, 13, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 10, 14, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 13, 15, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 13, 16, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 14, 5, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 14, 8, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 15, 7, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 15, 6, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 16, 15, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 16, 17, 18);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 16, 20, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 18, 7, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 18, 6, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 19, 7, -1);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (3, 19, 6, -1);