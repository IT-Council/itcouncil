# Clone Repository
```bash
git clone https://github.com/IT-Council/itcouncil.git
```

# Move Into Project
```bash
cd itcouncil
```

# Create Project Structure
```bash
mkdir -p src/main/java/com/example
mkdir -p src/main/webapp
```

# Create Files
```bash
touch src/main/java/com/example/LoginServlet.java
touch src/main/webapp/login.html
touch pom.xml
```

# Open in VS Code
```bash
code .
```

# Check Git Status
```bash
git status
```

# Add Files
```bash
git add .
```

# Commit Changes
```bash
git commit -m "Added Java login application"
```

# Push Code
```bash
git push origin main
```

# Pull Latest Changes
```bash
git pull origin main
```

# Install Snyk
```bash
npm install -g snyk
```

# Authenticate Snyk
```bash
snyk auth <SNYK_TOKEN>
```

# SCA Scan
```bash
snyk test
```

# SAST Scan
```bash
snyk code test
```

# IaC Scan
```bash
snyk iac test
```

# Upload Results to Dashboard
```bash
snyk monitor
```

# Build Docker Image
```bash
docker build -t myapp:1.0.0 .
```

# Scan Container Image
```bash
snyk container test myapp:1.0.0
```

# Create New Branch
```bash
git checkout -b feature-login
```

# Push Branch
```bash
git push origin feature-login
```

# View Git Logs
```bash
git log --oneline
```

# Check Branch
```bash
git branch
```




```yaml id="x4n7p2"
name: Java Snyk Security Pipeline

on:
  push:
    branches:
      - main

jobs:
  security-scan:

    runs-on: ubuntu-latest

    steps:

      # Checkout Source Code
      - name: Checkout Code
        uses: actions/checkout@v4

      # Setup Java
      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: 17

      # Build Maven Project
      - name: Build Application
        run: mvn clean install

      # Install Snyk CLI
      - name: Install Snyk
        run: npm install -g snyk

      # Authenticate Snyk
      - name: Authenticate Snyk
        run: snyk auth ${{ secrets.SNYK_TOKEN }}

      # SCA Scan
      - name: SCA Scan
        run: snyk test --severity-threshold=high

      # SAST Scan
      - name: SAST Scan
        run: snyk code test --severity-threshold=high

      # IaC Scan
      - name: IaC Scan
        run: snyk iac test

      # Upload Results to Dashboard
      - name: Monitor Project
        run: snyk monitor
```
