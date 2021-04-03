
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
    numParagraphNext INT,
    numParagraphConditional INT,
    CONSTRAINT fk_Choice_idBook FOREIGN KEY (idBook) REFERENCES Book(idBook) ON DELETE CASCADE,
    CONSTRAINT pk_Choice PRIMARY KEY (idBook, numParagraphCurrent)
);



--------------------------------------------------------------------------------
-- Remplissage de la base de donneés
--------------------------------------------------------------------------------


