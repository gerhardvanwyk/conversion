const {defaults} = require('jest-config');
var path = require('path');
console.log("rootDir: " + path.resolve(__dirname, "../"));
module.exports = {
    rootDir: path.resolve(__dirname, "../"),
    verbose: true,
    moduleFileExtensions: [
        defaults.moduleFileExtensions,
        "js",
        "ts",
        "vue"
    ],
    modulePaths: [
        "<rootDir>/src/components",
        "<rootDir>/node_modules"
    ],
    transform: {
        ".*\\.(vue)$": "<rootDir>/node_modules/vue-jest",
        ".*\\.(ts)$": "ts-jest"
    },
    moduleNameMapper: {
        "^@/(.*)$": "<rootDir>/src/$1"
    },
    globals: {
        "vue-jest": {
            "tsConfigFile": "tsconfig.json"
        }
    },
    testRegex: "(/__tests__/.*|(\\.|/)(test|spec))\\.(jsx?|tsx?)$"
};


