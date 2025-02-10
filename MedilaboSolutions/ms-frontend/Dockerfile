# BUILD
FROM node:18 AS build-stage

WORKDIR /app

# FILES
COPY package*.json ./
RUN npm install

COPY . .

RUN npm run build -- --configuration=docker --output-path=dist/ms-frontend --base-href=/ms-frontend/

FROM httpd:2.4

# Copie des fichiers Angular buildés vers Apache
COPY --from=build-stage /app/dist/ms-frontend/ /usr/local/apache2/htdocs/ms-frontend/

# Activer mod_rewrite pour le routing Angular
RUN sed -i '/LoadModule rewrite_module/s/^#//g' /usr/local/apache2/conf/httpd.conf

# Angular config
RUN echo "<Directory /usr/local/apache2/htdocs/ms-frontend>" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  Options Indexes FollowSymLinks" >> /usr/local/apache2/httpd.conf && \
    echo "  AllowOverride All" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  Require all granted" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  RewriteEngine On" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  RewriteCond %{REQUEST_FILENAME} !-f" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  RewriteCond %{REQUEST_FILENAME} !-d" >> /usr/local/apache2/conf/httpd.conf && \
    echo "  RewriteRule ^ /ms-frontend/index.html [L]" >> /usr/local/apache2/conf/httpd.conf && \
    echo "</Directory>" >> /usr/local/apache2/conf/httpd.conf

EXPOSE 80

CMD ["httpd-foreground"]