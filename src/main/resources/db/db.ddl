
#  ################## Implementation with annotations
#
# CREATE TABLE images (
#                         image_id BIGINT PRIMARY KEY AUTO_INCREMENT,
#                         label VARCHAR(100));
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