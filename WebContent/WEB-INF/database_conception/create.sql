-- ======================================== Create tables in the database ========================================

CREATE TABLE Content(
	id INT AUTO_INCREMENT,
	name VARCHAR(200) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(name)
);

-- /* Ajout d'une clé artificielle pour les performances */

CREATE TABLE Part(
	id INT AUTO_INCREMENT,
	content INT NOT NULL,
	beginning CHAR(12) NOT NULL,
	end CHAR(12) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(content, beginning),
	UNIQUE(content, end),
	CHECK (beginning LIKE '[0123456789][0123456789]:[0123456789][0123456789]:[0123456789][0123456789],[0123456789][0123456789][0123456789]'),
	FOREIGN KEY(content) REFERENCES Content(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- /* Ajout d'une clé artificielle pour les performances */

-- -----------------------------------------------------------------------------------------------

CREATE TABLE Language(
	id INT AUTO_INCREMENT,
	language VARCHAR(100) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE(language)
);

-- /* Ajout d'une clé artificielle pour les performances */

-- -----------------------------------------------------------------------------------------------

CREATE TABLE ContentLanguage(
	content INT,
	language INT,
	PRIMARY KEY(content, language),
	FOREIGN KEY(content) REFERENCES Content(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(language) REFERENCES Language(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------------------------------------------------

CREATE TABLE PartTranslation(
	part INT,
	language INT,
	content TEXT,
	PRIMARY KEY(part, language),
	FOREIGN KEY(part) REFERENCES Part(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(language) REFERENCES Language(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------------------------------------------------