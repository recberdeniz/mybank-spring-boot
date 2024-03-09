# pull official base image
FROM node:20.11.1-alpine

# set working directory
WORKDIR /mybank-fe

# add `/app/node_modules/.bin` to $PATH
ENV PATH /mybank-fe/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@5.0.1 -g --silent

# add app
COPY . ./

# start app
CMD ["npm", "start"]