git stash
git pull origin master
npm install
bower install
pm2 restart SamizdatNode
node node_modules/mongo-migrate -runmm  -cfg config/db-config.json -dbn dbSettings up
#pm2 start server.js --name SamizdatNode
