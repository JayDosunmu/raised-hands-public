# base image
FROM node:12.2.0-alpine

# set working directory
WORKDIR /app

# install and cache app server dependencies
COPY server/ .
RUN npm install --silent

# Import browser app build
COPY build/ ./static

# start app
CMD ["npm", "start"]
