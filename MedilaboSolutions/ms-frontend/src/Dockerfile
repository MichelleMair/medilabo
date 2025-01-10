# BUILD
FROM node:18 AS build-stage

WORKDIR /app

# FILES
COPY package*.json ./
RUN npm install

COPY . .

RUN npm run build -- --output-path=dist/ms-frontend --base-href=/ms-frontend/

FROM httpd:2.4

# Apache config
RUN echo "LoadModule rewrite_module modules/mod_rewrite.so" >> /usr/local/apache2/conf/httpd.conf
RUN echo "<Directory /usr/local/apache2/htdocs/ms-frontend>" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  Options Indexes FollowSymLinks" >> /usr/local/apache2/httpd.conf && \
    echo "  AllowOverride All" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  Require all granted" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  RewriteEngine On" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  RewriteCond %{REQUEST_FILENAME} !-f" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  RewriteRule ^ index.html [QSA,L]" >> /usr/local/apache2/conf/httpd.conf && \
    echo "</Directory>" >> /usr/local/apache2/conf/httpd.conf

EXPOSE 80

CMD ["httpd-foreground"]