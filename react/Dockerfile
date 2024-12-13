

# Build The React Application
FROM node:20-alpine 

# Set the working environment 
WORKDIR /app

# Copy package files and install dependencies 
COPY package*.json . 

#run the build
RUN npm install

COPY . . 

# Expose port
EXPOSE 3000 

CMD [ "npm","run","dev"]

