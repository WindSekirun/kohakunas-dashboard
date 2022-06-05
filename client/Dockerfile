FROM node:16 AS frontend
WORKDIR /app
COPY package*.json ./
RUN yarn
COPY . .
RUN yarn build

FROM nginx:stable-alpine AS nginx
COPY --from=frontend /app/dist/ /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;" ]