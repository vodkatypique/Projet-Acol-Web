
--------------------------------------------------------------------------------
-- Création de la base de donneés
--------------------------------------------------------------------------------

DROP TABLE Choice;
DROP TABLE UserBookHistory;
DROP TABLE UserAccess;
DROP TABLE Paragraph;
DROP TABLE UserTable;
DROP TABLE Book;


CREATE TABLE Book(
    idBook INT NOT NULL,
    titleBook VARCHAR(50),
    isPublished INT, --boolean
    isOpen INT, --boolean
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
    idUser INT NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    CONSTRAINT pk_userTable PRIMARY KEY (idUser)
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
    numJump INT,
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



--------------------------------------------------------------------------------
-- Remplissage de la base de donneés
--------------------------------------------------------------------------------

INSERT INTO Book(idBook, titleBook, isPublished, isOpen)
VALUES (1, 'Les aventures de Shrek !', 1, 1);
INSERT INTO Book(idBook, titleBook, isPublished, isOpen)
VALUES (2, 'Les aventures de Shrek 2 !', 0, 1);
INSERT INTO Book(idBook, titleBook, isPublished, isOpen)
VALUES (3, 'La forêt maudite', 0, 0);


INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (1, 1, 'Il était une fois, dans un marée,',  
'un joli ogre tout vert y vivait paisiblement dans un tronc d''arbre.',
 'Thibault', 0, 1, 1);
INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (1, 2, 'Vous êtes un chevalier en mission pour attaquer un dragon.',
 'Cependant, en vous aventurant dans ce même marée pour chercher le dragon, 
vous vous retrouvez né à né avec Shrek.
 A première vu il vous effraye mais il n''a pas l''air méchant, 
peut être qu''il vous aidera dans votre mission si vous demandez gentillement que faites vous ?',
 'Thibault', 0, 1, 1);
INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (1, 3, 'Vous décidez d''attaquer l''ogre.', 
'L''ogre esquive votre épée et vous vous enfuyait lâchement. 
Vous avez découvert ce qu''était un ogre énervé 
mais vous ne pouvez pas continuer votre mission sans votre épée. Vous avez perdu !',
 'Thibault', 1, 1, 1);
INSERT INTO Paragraph(idBook, numParagraph, paragraphTitle, text, author, isEnd, isValidate, isAccessible)
VALUES (1, 4, 'Vous décidez de demander de l''aide à l''ogre pour terasser le dragon.',
 'L''ogre semble comprendre vos mots mais ne semble pas adhérer à votre cause. 
Il vous hurle dessus et vous demande de partir de son marée. 
Au moins, vous ne vous êtes pas fait mangé. Vous avez gagné !',
 'Mathieu', 1, 1, 1);

-- TODO rajouter des histoires pour les livres 2 et 3, ouvertes ou non




INSERT INTO UserTable(idUser, login, password)
VALUES (1, 'Thibault', 'zjemgkjmihg');
INSERT INTO UserTable(idUser, login, password)
VALUES (2, 'Mathieu', '1234');
INSERT INTO UserTable(idUser, login, password)
VALUES (3, 'vodka', 'baccareccia2B');


INSERT INTO UserAccess(idBook, idUser)
VALUES (1, 1);
INSERT INTO UserAccess(idBook, idUser)
VALUES (1, 2);
INSERT INTO UserAccess(idBook, idUser)
VALUES (2, 1);
INSERT INTO UserAccess(idBook, idUser)
VALUES (3, 2);


-- l'historique se vérifiera par la pratique


INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (1, 1, 2, NULL);
INSERT INTO Choice(idBook, numParagraphCurrent, numParagraphNext, numParagraphConditional)
VALUES (1, 2, 3, NULL);
