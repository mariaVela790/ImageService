CREATE TABLE images (
    image_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    label VARCHAR(50));

CREATE TABLE objects (
    object_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    object VARCHAR(50));

# CREATE TABLE image_objects (
#       image_id BIGINT,
#       object_id BIGINT,
#       CONSTRAINT image_objects_id PRIMARY KEY (image_id, object_id),
#       CONSTRAINT FK_image FOREIGN KEY (image_id) REFERENCES images (image_id),
#       CONSTRAINT FK_object FOREIGN KEY (object_id) REFERENCES objects (object_id));
#
# CREATE TABLE image_annotations (
#       image_id BIGINT,
#       object_id BIGINT,
#       score FLOAT(15),
#       topicality FLOAT(15)
#       CONSTRAINT image_objects_id PRIMARY KEY (image_id, object_id),
#       CONSTRAINT FK_image FOREIGN KEY (image_id) REFERENCES images (image_id),
#       CONSTRAINT FK_object FOREIGN KEY (object_id) REFERENCES objects (object_id));

-- INSERT INTO objects (object)
-- VALUES ("rose");

-- ################################ SEED VALUES
-- SELECT * from objects where object = "c-- at";

-- INSERT INTO images (label) VALUES ("puppy_photo");

-- SELECT * FROM images;
-- SELECT * FROM objects;

-- INSERT INTO image_objects (image_id, object_id) VALUES (1,4)
-- INSERT INTO image_objects (image_id, object_id) VALUES (1,1);
-- INSERT INTO image_objects (image_id, object_id) VALUES (2,5);
-- INSERT INTO image_objects (image_id, object_id) VALUES (2,6);
-- INSERT INTO image_objects (image_id, object_id) VALUES (3,2);
-- INSERT INTO image_objects (image_id, object_id) VALUES (3,3);

--  ######## Get objects for an image
-- SELECT object FROM objects
-- WHERE object_id IN (SELECT (object_id) FROM image_objects
--                                                 LEFT JOIN images
--                                                           ON image_objects.image_id = images.image_id
--                     WHERE images.image_id IN (1))


#  ################## Implementation with annotations
#
# CREATE TABLE images (
#                         image_id BIGINT PRIMARY KEY AUTO_INCREMENT,
#                         label VARCHAR(50));
#
# CREATE TABLE objects (
#                          object_id BIGINT PRIMARY KEY AUTO_INCREMENT,
#                          object VARCHAR(50));
#
#
# CREATE TABLE image_objects (
#                                image_object_id BIGINT PRIMARY KEY AUTO_INCREMENT,
#                                image_id BIGINT,
#                                object_id BIGINT,
#                                CONSTRAINT UC_image_object UNIQUE (image_id, object_id),
#                                CONSTRAINT FK_image FOREIGN KEY (image_id) REFERENCES images (image_id),
#                                CONSTRAINT FK_object FOREIGN KEY (object_id) REFERENCES objects (object_id));
#
# CREATE TABLE image_annotations (
#                                    annotation_id BIGINT PRIMARY KEY AUTO_INCREMENT,
#                                    image_object_id BIGINT,
#                                    score FLOAT(15),
#                                    topicality FLOAT(15),
#                                    CONSTRAINT FK_image_object FOREIGN KEY (image_object_id) REFERENCES image_objects (image_object_id));