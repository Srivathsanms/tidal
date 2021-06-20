DROP TABLE IF EXISTS PLAYLIST;
CREATE TABLE PLAYLIST (
UUID VARCHAR(50) NOT NULL,
Playlist_Name VARCHAR(50) NOT NULL,
Number_of_Tracks INT(8) NOT NULL,
Last_Updated_date DATE NOT NULL,
Registered_date DATE NOT NULL,
Deleted NUMERIC (1) DEFAULT 0,
Duration FLOAT (2) NOT NULL
);


DROP TABLE IF EXISTS PLAYLIST_AND_TRACK;
CREATE TABLE PLAYLIST_AND_TRACK(
PLAYLIST_ID VARCHAR(50) NOT NULL,
TRACK_ID INT(8) NOT NULL,
TRACK_INDEX INT(3) NOT NULL,
DATE_ADDED DATE NOT NULL
);

DROP TABLE IF EXISTS TRACKS;
CREATE TABLE TRACKS(
TRACK_ID INT(8) NOT NULL,
ARTIST_ID INT(8) NOT NULL,
TITLE VARCHAR(50) NOT NULL,
Duration FLOAT (2) NOT NULL
);
