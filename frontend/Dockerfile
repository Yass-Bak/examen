# Build Angular app
FROM node:18 AS build
WORKDIR /app

# Install dependencies
COPY package*.json ./

RUN npm install --legacy-peer-deps

# Build the Angular app
COPY . .
RUN npm run build --prod

# Serve with NGINX
FROM nginx:1.21
COPY --from=build /app/dist/Spike /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
